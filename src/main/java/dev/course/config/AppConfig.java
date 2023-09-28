package dev.course.config;

import dev.course.manager.ConsoleManager;
import dev.course.service.LibraryManagement;

/**
 * DI 컨테이너가 아닌 자바 애플리케이션 상에서 의존성을 주입해주고자 Config 클래스를 둠
 * Config 클래스는 사용자가 사용할 LibraryManagement 인스턴스를 (모드에 따라) 적절하게 반환해줌
 *
 * AppConfig 클래스는 이외의 모든 Config 클래스를 어우르는 클래스
 * 입력으로 들어온 modeId에 따라 적절한 구현체를 주입해서 LibraryManagement 인스턴스를 반환해줌
 */

public class AppConfig {

    public LibraryManagement getLibrary(int modeId) {
        return new LibraryManagement(getLibraryConfig(modeId));
    }

    /**
     * LibraryConfig 는 ModeConfig / CallbackConfig / ConsoleManager 를 아우르는 클래스
     * 1. modeId에 맞는 Mode 구현체를 주입받음 -> ModeConfig
     * 2. 해당 모드에 필요한 콜백함수에 적절한 인자를 전달해줌 -> injectCallback
     */

    public LibraryConfig getLibraryConfig(int modeId) {
        return new LibraryConfig(getModeConfig(modeId), getCallbackConfig(modeId), getConsoleManager());
    }

    public ModeConfig getModeConfig(int modeId) {
        return new ModeConfig(modeId);
    }

    public CallbackConfig getCallbackConfig(int modeId) {
        CallbackConfig callbackConfig = new CallbackConfig(getModeConfig(modeId));
        callbackConfig.injectCallback(modeId);
        return callbackConfig;
    }

    public ConsoleManager getConsoleManager() {
        return new ConsoleManager();
    }
}