//package com.custommacro.custommacro.scheduledMacro.service;
//
//import com.custommacro.custommacro.scheduledMacro.domain.KeyMacro;
//import java.awt.event.KeyEvent;
//import java.util.concurrent.TimeUnit;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//class KeyMacroServiceTest {
//    private KeyMacroService keyMacroService;
//    private KeyMacro keyMacro;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        keyMacroService = Mockito.spy(new KeyMacroService());
//
//        keyMacro = new KeyMacro();
//        keyMacro.setKeyCode(KeyEvent.VK_A);
//        keyMacro.setInterval(1000);
//    }
//
//    @Test
//    @DisplayName("키 매크로 실행 테스트")
//    void testStartMacro() {
//        //given
//
//        //when
//        keyMacroService.startMacro(keyMacro);
//
//        //then
//        Assertions.assertTrue(keyMacroService.isRunning());
//    }
//
//    @Test
//    @DisplayName("키 매크로 중지 테스트")
//    void testStopMacro() {
//        //given
//        keyMacroService.startMacro(keyMacro);
//
//        //when
//        keyMacroService.stopMacro();
//
//        //then
//        Assertions.assertFalse(keyMacroService.isRunning());
//    }
//
//    @Test
//    @DisplayName("키 매크로 동작 테스트")
//    void testExecute() throws Exception {
//        //given
//
//        //when
//        keyMacroService.startMacro(keyMacro);
//        TimeUnit.SECONDS.sleep(2);
//
//        //then
//        Mockito.verify(keyMacroService, Mockito.atLeastOnce()).performKeyPress(keyMacro.getKeyCode());
//
//        keyMacroService.stopMacro();
//    }
//}