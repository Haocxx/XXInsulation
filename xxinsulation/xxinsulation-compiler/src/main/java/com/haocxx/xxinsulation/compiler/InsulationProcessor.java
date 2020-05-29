package com.haocxx.xxinsulation.compiler;

import com.haocxx.xxinsulation.annotation.Insulator;
import com.haocxx.xxinsulation.annotation.Synthetic;
import com.haocxx.xxinsulation.interfaces.IServer;
import com.haocxx.xxinsulation.interfaces.InsulatorList;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
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
import javax.tools.FileObject;
import javax.tools.StandardLocation;

/**
 * Created by Haocxx
 * on 2020-04-26
 */
public class InsulationProcessor extends AbstractProcessor {
    private static final String TAG = "InsulationProcessor";
    private static final String PACKAGE_NAME = "insulatorPackageName";
    private static final String SERVER_CLASS_NAME = "Insulation_Server";
    private static final String DEFAULT_META_FILE_FOLDER = "META-INF/services/";
    private boolean mHasProcessed;
    private String mPackageName;
    private Filer mFiler;
    private Writer mWriter;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        String fullName = processingEnv.getOptions().get(PACKAGE_NAME);
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
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Override.class)
                .addParameter(ParameterSpec.builder(InsulatorList.class, "insulatorList").build())
                .returns(void.class);

        for (Element element : roundEnvironment.getElementsAnnotatedWith(Insulator.class)) {
            getInsulatorClasses.addStatement("insulatorList.add($L.class)",
                ((TypeElement)element).getQualifiedName().toString());
        }

        TypeSpec clazz = TypeSpec.classBuilder(SERVER_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Synthetic.class)
                .addSuperinterface(IServer.class)
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
        appendMetaFile(mPackageName + "." + SERVER_CLASS_NAME);
        mHasProcessed = true;
        return false;
    }

    private void appendMetaFile(String line) {
        Writer writer = openWriter();
        try {
            writer.append(line);
            writer.append("\n");
            writer.flush();
        } catch (IOException e) {}
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (mWriter != null) {
            mWriter.close();
        }
    }

    private Writer openWriter() {
        if (mWriter != null) {
            return mWriter;
        }
        FileObject source;
        try {
            source = mFiler.createResource(StandardLocation.CLASS_OUTPUT, "",
                DEFAULT_META_FILE_FOLDER + IServer.class.getName());
            mWriter = source.openWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mWriter;
    }
}
