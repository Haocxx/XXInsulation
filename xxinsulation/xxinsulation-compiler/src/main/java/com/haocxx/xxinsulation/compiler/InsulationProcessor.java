package com.haocxx.xxinsulation.compiler;

import com.haocxx.xxinsulation.annotation.Insulator;
import com.haocxx.xxinsulation.annotation.Synthetic;
import com.haocxx.xxinsulation.interfaces.InsulatorList;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by Haocxx
 * on 2020-04-26
 */
public class InsulationProcessor extends AbstractProcessor {
    private static final String TAG = "InsulationProcessor";
    private static final String CLASS_NAME = "insulatorPackageName";
    private boolean mHasProcessed;
    private String mPackageName;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        String fullName = processingEnv.getOptions().get(CLASS_NAME);
        if (fullName == null) {
            mHasProcessed = true;
            return;
        }
        mPackageName = fullName;
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
        if (mHasProcessed) {
            return false;
        }

        MethodSpec.Builder getInsulatorClasses = MethodSpec.methodBuilder("getInsulatorClasses")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .addParameter(ParameterSpec.builder(InsulatorList.class, "insulatorList").build())
                .returns(void.class);

        for (Element element : roundEnvironment.getElementsAnnotatedWith(Insulator.class)) {
            getInsulatorClasses.addStatement("insulatorList.add($L.class)",
                ((TypeElement)element).getQualifiedName().toString());
        }

        TypeSpec clazz = TypeSpec.classBuilder("Insulation_Server")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Synthetic.class)
                .addMethod(getInsulatorClasses.build())
                .build();

        JavaFile javaFile = JavaFile.builder(mPackageName, clazz)
                .build();

        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            System.out.println(TAG + ": process failed");
            e.printStackTrace();
        }
        mHasProcessed = true;
        return false;
    }
}
