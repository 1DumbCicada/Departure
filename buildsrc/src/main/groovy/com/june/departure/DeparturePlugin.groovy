package com.june.departure

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by cxmy on 2017/4/12.
 */
public class DeparturePlugin implements Plugin<Project> {

    void apply(Project project) {
        def android = project.extensions.findByType(AppExtension)
        android.registerTransform(new DepartureTransform(project))
    }
}