package com.dmdev.junit.service;

import com.dmdev.junit.dao.UserDao;
import com.dmdev.junit.dto.User;
import com.dmdev.junit.service.extension.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.RepeatedTest.LONG_DISPLAY_NAME;


@Tag("fast")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({

        UserServiceParamResolver.class,
//        GlobalExtensios.class,
        PostProcEx.class,
        ConditionalEx.class,
        MockitoExtension.class
//        ThrowableEx.class

})

class UserServiceTest {

    private static final User IVAN = User.of(1, "Ivan", "123");
    private static final User OVAN = User.of(2, "Ovan", "123");

    @Captor
    private ArgumentCaptor<Integer> argumentCaptor;
    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;

    UserServiceTest(TestInfo testInfo){
        System.out.println();
    }

    @BeforeAll
    void init() {
        System.out.println("before all: "+ this);
    }

    @BeforeEach
    void prepare() {
        System.out.println("dsadasdasd "+ this);
//
    }

    @Test
    void shouldDeleteExistingUser() {
        userService.add(IVAN);
        Mockito.doReturn(true).when(userDao).delete(IVAN.getId());
//        Mockito.doReturn(true).when(userDao).delete(Mockito.any());

//        Mockito.when(userDao.delete(IVAN.getId())).
//                thenReturn(true)
//                .thenReturn(false);

        boolean deleteResult = userService.delete(IVAN.getId());
        System.out.println(userService.delete(IVAN.getId()));
        System.out.println(userService.delete(IVAN.getId()));

        Mockito.verify(userDao, Mockito.times(3)).delete(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(IVAN.getId());
        

        assertThat(deleteResult).isTrue();
    }

    @Test
    @DisplayName("gthdsq")
    void usersEmptyIfNoUserAdded(UserService userService) throws IOException {
            if (true){
                throw new RuntimeException();
            }
         System.out.println("1 "+ this);

        List<User> users = userService.getAll();
        assertTrue(users.isEmpty(), () -> "us be");
    }

    @Test
    void usersSizeIfUserAdded() {

        System.out.println("2 "+ this);

        userService.add(IVAN);
        userService.add(OVAN);

        List<User> users = userService.getAll();

        assertThat(users).hasSize(2);
//        assertEquals(2,users.size(), () -> "us be");
    }



    @Test
    void usersConvertedToMapId() {
        userService.add(IVAN,OVAN);

        Map<Integer,User> users = userService.getAllConById();

        assertAll(
                () ->    assertThat(users).containsKeys(1,2),
                () ->  assertThat(users).containsValues(IVAN,OVAN)
        );
    }



    @AfterEach
    void deleteDataFromDatabase() {
        System.out.println("After " + this +"\n");
    }

    @AfterAll
    void closeConnectionPool() {
        System.out.println("after all: " + this);
    }

    @Nested
    @DisplayName("test user log")
    @Tag("log")
    class LoginTest {

        @Test
        void loginFailIfPasswordIsNotCorrect() {

            userService.add(IVAN);
            Optional<User> maybeUser = userService.login(IVAN.getUsername(), "dum");

            assertTrue(maybeUser.isEmpty());
        }

        @RepeatedTest(value = 5, name = LONG_DISPLAY_NAME)
        void loginFailIfPasswordIsNotExist(RepetitionInfo repetitionInfo) {
            userService.add(IVAN);
            Optional<User> maybeUser = userService.login("IVAN.getUsername()", IVAN.getPassword());

            assertTrue(maybeUser.isEmpty());
        }

        @Test
        @Disabled("none")
        void checlog(){
            System.out.println(Thread.currentThread().getName());
            Optional<User> result = assertTimeoutPreemptively(Duration.ofMillis(200L), () -> {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(300L);
                return userService.login("IVAN.getUsername()", IVAN.getPassword());
            });
        }

        @Test
        void loginSuccessIfUserExists() {
            userService.add(IVAN);
            Optional<User> maybeUser = userService.login(IVAN.getUsername(), IVAN.getPassword());

            assertThat(maybeUser).isPresent();
            maybeUser.ifPresent(user -> assertThat(user).isEqualTo(IVAN));
//        assertTrue(maybeUser.isPresent());
//        maybeUser.ifPresent(user -> assertEquals(IVAN, user));
        }

        @Test
        void throwExpIfUserOrPassIsNull() {
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class, () -> userService.login(null, "aa")),
                    () -> assertThrows(IllegalArgumentException.class, () -> userService.login("null", null))
            );
        }

        @ParameterizedTest(name = "{argument} test")
        @Disabled("flaky/ not need")
        @MethodSource("com.dmdev.junit.service.UserServiceTest#getArgForLogTest")
        @DisplayName("login param")

        void loginParamTest(String username, String password, Optional<User> user) {
            userService.add(IVAN, OVAN);

            Optional<User> maybeUser = userService.login(username, password);
            assertThat(maybeUser).isEqualTo(user);
        }

    }
        static Stream<Arguments> getArgForLogTest() {
            return Stream.of(
                    Arguments.of("Ivan","123",Optional.of(IVAN)),
                    Arguments.of("Ovan","123",Optional.of(OVAN)),
                    Arguments.of("Ovan","aaa",Optional.empty()),
                    Arguments.of("aaa","123",Optional.empty())
            );
        }
    }


