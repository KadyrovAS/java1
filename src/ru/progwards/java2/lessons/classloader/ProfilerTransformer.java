package ru.progwards.java2.lessons.classloader;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class ProfilerTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        byte[] byteCode = classfileBuffer;

        if (className.endsWith("Profiler")) {

            String clName = className.replaceAll("/",".");
            try {
                ClassPool pool = ClassPool.getDefault();
                CtClass ctClass = pool.get(clName);
                CtMethod ctMethod = ctClass.getDeclaredMethod("main");
                ctMethod.addLocalVariable("start", CtClass.longType);
                ctMethod.insertBefore("start = System.currentTimeMillis();");
                ctMethod.insertAfter("System.out.println(\"время выполнения \" + (System.currentTimeMillis() - start));");

                try {
                    byteCode = ctClass.toBytecode();
                    ctClass.detach();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }

        }
        return byteCode;
    }
}
