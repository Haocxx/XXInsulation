package com.haocxx.xxinsulation.compiler;

import com.google.auto.service.AutoService;
import com.haocxx.xxinsulation.annotation.Insulator;
import com.haocxx.xxinsulation.annotation.Synthetic;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by Haocxx
 * on 2020-04-26
 */
@AutoService(Processor.class)
public class InsulationProcessor extends AbstractProcessor {
    public static final String TAG = "InsulationProcessor";
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(Insulator.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        MethodSpec getInsulatorClasses = MethodSpec.methodBuilder("getInsulatorClasses")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        TypeSpec clazz = TypeSpec.classBuilder("Insulation_Server")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Synthetic.class)
                .addMethod(getInsulatorClasses)
                .build();

        JavaFile javaFile = JavaFile.builder("com.haocxx.xxinsulation", clazz)
                .build();

        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            System.out.println(TAG + ": process failed");
            e.printStackTrace();
        }

        return true;
    }
}
