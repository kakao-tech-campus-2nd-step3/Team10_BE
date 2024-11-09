package poomasi.domain.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.member.entity.Member;
import poomasi.domain.store.dto.StoreRegisterRequest;
import poomasi.domain.store.dto.StoreResponse;
import poomasi.domain.store.entity.Store;
import poomasi.domain.store.repository.StoreRepository;
import poomasi.domain.store.service.StoreService;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private Member testMember;

    @BeforeEach
    void setUp() {
        // 테스트 멤버와 Authentication 설정
        testMember = Member.builder().build();

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new UserDetailsImpl(testMember));
    }

    @Test
    void addStore_StoreAddedSuccessfully() {
        // given
        StoreRegisterRequest request = mock(StoreRegisterRequest.class);
        Store store = mock(Store.class);

        when(request.toEntity(any(Member.class))).thenReturn(store);

        // when
        storeService.addStore(request);

        // then
        verify(storeRepository, times(1)).save(store);
    }

    @Test
    void getStore_StoreExists_ReturnStoreResponse() {
        // given
        Store store = new Store(1L, "test","test","test",testMember,"test","test");
        when(storeRepository.findByOwnerId(testMember.getId())).thenReturn(Optional.of(store));

        // when
        StoreResponse response = storeService.getStore();

        // then
        assertNotNull(response);
        verify(storeRepository, times(1)).findByOwnerId(testMember.getId());
    }

    @Test
    void updateStore_StoreExists_StoreUpdatedSuccessfully() {
        // given
        StoreRegisterRequest request = mock(StoreRegisterRequest.class);
        Store store = mock(Store.class);

        when(storeRepository.findByOwnerId(testMember.getId())).thenReturn(Optional.of(store));

        // when
        storeService.updateStore(request);

        // then
        verify(store, times(1)).updateStore(request);
    }

    @Test
    void updateStore_StoreDoesNotExist_ThrowsBusinessException() {
        // given
        StoreRegisterRequest request = mock(StoreRegisterRequest.class);
        when(storeRepository.findByOwnerId(testMember.getId())).thenReturn(Optional.empty());

        // when & then
        BusinessException exception = assertThrows(BusinessException.class, () -> storeService.updateStore(request));
        assertEquals(BusinessError.STORE_NOT_FOUND, exception.getBusinessError());
    }

}