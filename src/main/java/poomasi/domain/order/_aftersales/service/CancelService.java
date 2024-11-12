package poomasi.domain.order._aftersales.service;

import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;

public interface CancelService<T, P> {
    T cancel(P parameter) throws IOException, IamportResponseException;
}
