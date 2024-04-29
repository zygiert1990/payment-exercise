package com.verestro.exercise.payment.service.account;

import com.verestro.exercise.payment.model.AccountNumber;
import com.verestro.exercise.payment.model.UserDTO;
import com.verestro.exercise.payment.security.UsernameProvider;
import com.verestro.exercise.payment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import static com.verestro.exercise.payment.model.AccountCode.CODE_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CreateAccountServiceTest {

    @Mock
    private UsernameProvider usernameProvider;

    @Mock
    private UserService userService;

    @InjectMocks
    private CreateAccountService createAccountService;

    @BeforeEach
    void setup() {
        when(usernameProvider.provideUsername()).thenReturn("username");
    }

    @Test
    void shouldThrowErrorWhenUserAlreadyHasAnAccount() {
        // given
        when(userService.hasAccount(anyString())).thenReturn(true);
        // when && then
        assertThatThrownBy(this::createAccount).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void shouldAssignAccountToUser() {
        // given
        when(userService.hasAccount(anyString())).thenReturn(false);
        when(userService.findByUsername(anyString())).thenReturn(UserDTO.builder().build());
        when(userService.save(any())).thenAnswer(answer -> answer.getArgument(0, UserDTO.class));
        // when
        AccountNumber result = createAccount();
        // then
        assertThat(result.value()).hasSize(20);
    }

    private AccountNumber createAccount() {
        return createAccountService.createAccount(CODE_1);
    }

}