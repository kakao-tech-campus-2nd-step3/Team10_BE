package poomasi.domain.auth.token.refreshtoken.repository;

import java.util.List;

public interface RefreshTokenRepository {

    void save();
    String findById();
    List<String> findAll();
    void delete(String id);

}
