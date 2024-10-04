package poomasi.domain.auth.reissue.repository;

import java.util.List;

public interface RefreshTokenRepository {

    void save();
    String findById();
    List<String> findAll();
    void delete(String id);

}
