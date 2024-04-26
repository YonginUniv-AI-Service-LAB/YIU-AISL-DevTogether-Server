package yiu.aisl.devTogether.repository;

import org.springframework.data.repository.CrudRepository;
import yiu.aisl.devTogether.domain.Token;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, String> {



}
