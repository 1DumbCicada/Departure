package com.june.departure

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.CtClass
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import java.util.jar.JarInputStream

/**
 * Created by cxmy on 2017/4/12.
 */
public class DepartureTransform extends Transform {
    private CtClass ctClass
    Project project

    // 构造函数，我们将Project保存下来备用
    public DepartureTransform(Project project) {
        this.project = project
    }

    // 设置我们自定义的Transform对应的Task名称
    @Override
    String getName() {
        return "DepartureTrans"
    }

    // 指定输入的类型，通过这里的设定，可以指定我们要处理的文件类型
    //这样确保其他类型的文件不会传入
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    // 指定Transform的作用范围
    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider, boolean isIncremental)
            throws IOException, TransformException, InterruptedException {
        List<DirectoryInput> dirInputs = new ArrayList<>()
        List<JarInput> jarInputs = new ArrayList<>()
        // Transform的inputs有两种类型，一种是项目内的目录，一种是第三方的jar包，要分开遍历
        TransformInput input;
        for (int i = 0; i < inputs.size(); i++) {
            input = inputs.get(i);
            if (!input.getJarInputs().isEmpty()) {
                jarInputs.addAll(input.getJarInputs())
            } else if (!input.getDirectoryInputs().isEmpty()) {
                dirInputs.addAll(input.getDirectoryInputs())
            }
        }
        if (!dirInputs.isEmpty()) {
            DirectoryInput directoryInput;
            for (int i; i < dirInputs.size(); i++) {
                directoryInput = dirInputs.get(i);
                L.i("directoryInput :" + directoryInput.file.absolutePath);
                //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
                DepartureInject.injectConstructor(directoryInput.file.absolutePath, "com\\june\\departure")
                // 获取output目录
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)

                // 将input的目录复制到output指定目录
                FileUtils.copyDirectory(directoryInput.file, dest)

            }
        }
        if (!jarInputs.isEmpty()) {
            JarInput jarInput
            for (int i = 0; i < jarInputs.size(); i++) {
                jarInput = jarInputs.get(i);
                //对第三方的 jar 包文件，进行遍历
                // 重命名输出文件（同目录copyFile会冲突）
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                File copyJarFile = jarInput.file;
                def libJar = jarInput.file.getAbsoluteFile().name;


                if (libJar.contains("thirdlib.jar")) {
                    L.i("start unzip :" + libJar)
                    JarZipUtil.unzipJar(jarInput.file.getAbsolutePath(), ".\\tmp\\lib");
                    DepartureInject.injectConstructor(".\\tmp\\lib", "com\\june\\thirdlib")
                    JarZipUtil.zipJar(".\\tmp\\lib", ".\\tmp\\thirdlib.jar")
                    copyJarFile = new File(".\\tmp\\thirdlib.jar");
                }

                //生成输出路径
                def dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                L.i("===============")
                L.i("jar dest :" + dest);
                L.i("jar jarInput.file :" + copyJarFile);
                //将输入内容复制到输出
                if (!copyJarFile.absolutePath.contains("support-v4")) {
                    L.i("jar jarInput.file 2:" + copyJarFile);
                    FileUtils.copyFile(copyJarFile, dest)
                }
            }
        }

    }
}