package com.simplifiedBanking.services;

import com.simplifiedBanking.domain.user.User;
import com.simplifiedBanking.domain.user.UserType;
import com.simplifiedBanking.dtos.TransactionDTO;
import com.simplifiedBanking.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private UserService userService;

    @Mock
    private AuthorizationService authorize;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create transaction successfully when everything is OK")
    void createTransactionCase1() throws Exception {
        User sender = new User(1L, "Anderson", "Palmerim", "99999999912", "anderson@gmail.com", "123456", new BigDecimal(10), UserType.COMMAN);
        User receiver = new User(2L, "Andre", "Palmerim", "99999999913", "anderson12@gmail.com", "123456", new BigDecimal(10), UserType.MERCHANT);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(authorize.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);
        transactionService.createTransaction(request);

        verify(repository, times(1)).save(any());

        sender.setBalance(new BigDecimal(0));
        verify(userService, times(1)).saveUser(sender);

        receiver.setBalance(new BigDecimal(20));
        verify(userService, times(1)).saveUser(receiver);
    }

    @Test
    @DisplayName("Should throw Exception when Transaction is not allowed")
    void createTransactionCase2() throws Exception {
        User sender = new User(1L, "Anderson", "Palmerim", "99999999912", "anderson@gmail.com", "123456", new BigDecimal(10), UserType.COMMAN);
        User receiver = new User(2L, "Andre", "Palmerim", "99999999913", "anderson12@gmail.com", "123456", new BigDecimal(10), UserType.COMMAN);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(authorize.authorizeTransaction(any(), any())).thenReturn(false);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);
            transactionService.createTransaction(request);
        });

        Assertions.assertEquals("transação não autorizada", thrown.getMessage());

    }
}