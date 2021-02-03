package ru.progwards.java2.lessons.patterns;

public class ThreadProfiler implements Runnable{
    Profiler profiler;
    @Override
    public void run() {
        profiler = Profiler.getInstance();
        try {
            sectionsUse();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void sectionsUse() throws InterruptedException {
        for (int i = 0; i < 10; i ++) {
            profiler.enterSection("1");
            Thread.sleep(5000);
            profiler.enterSection("2");
            Thread.sleep(1000);
            profiler.enterSection("3");
            Thread.sleep(2000);
            profiler.exitSection("3");
            profiler.exitSection("2");
            Thread.sleep(1000);
            profiler.enterSection("3");
            Thread.sleep(1000);
            profiler.exitSection("3");
            profiler.exitSection("1");
        }
    }
}