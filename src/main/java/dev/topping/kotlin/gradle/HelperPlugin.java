package dev.topping.kotlin.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginAware;
import org.gradle.api.plugins.PluginManager;

import java.io.*;

public class HelperPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        //System.out.println("Creating files for project");
        copyFiles(project);
        /*project.beforeEvaluate(projectIn -> {
            System.out.println("Creating files for project");
            copyFiles(projectIn);
        });*/
        /*project.getTasksByName("preBuild", false).stream().findFirst().get()
        .doFirst(task -> {
            System.out.println("Creating files for project");
            copyFiles(task.getProject());
        });*/
        //project.getPluginManager().apply("./kotlinprocessor.gradle");
    }

    private void copyFiles(Project project) {
        //System.out.println("kotlinprocessor.gradle");
        File kotlinprocessor = new File(project.getProjectDir(), "kotlinprocessor.gradle");
        if(kotlinprocessor.exists())
            kotlinprocessor.delete();
        try {
            kotlinprocessor.createNewFile();
        } catch (IOException e) {
        }
        try {
            copyFileUsingStream(getClass().getClassLoader().getResourceAsStream("kotlinprocessor.gradle"), kotlinprocessor);
        } catch (IOException e) {
        }

        //System.out.println("parser.jar");
        File parser = new File(project.getProjectDir(), "parser.jar");
        if(parser.exists())
            parser.delete();
        try {
            parser.createNewFile();
        } catch (IOException e) {
        }
        try {
            copyFileUsingStream(getClass().getClassLoader().getResourceAsStream("parser.jar"), parser);
        } catch (IOException e) {
        }

        //System.out.println("viewbinding.jar");
        File viewbinding = new File(project.getProjectDir(), "viewbinding.jar");
        if(viewbinding.exists())
            viewbinding.delete();
        try {
            viewbinding.createNewFile();
        } catch (IOException e) {
        }
        try {
            copyFileUsingStream(getClass().getClassLoader().getResourceAsStream("viewbinding.jar"), viewbinding);
        } catch (IOException e) {
        }
    }

    private static void copyFileUsingStream(InputStream is, File dest) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
}
