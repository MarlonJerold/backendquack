package com.quackfinances.quackfinances;

import com.quackfinances.quackfinances.model.Account;
import com.quackfinances.quackfinances.model.UserModel;
import com.quackfinances.quackfinances.repository.AccountRepository;
import com.quackfinances.quackfinances.repository.UserRepository;
import com.quackfinances.quackfinances.view.controller.AccountController;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MeuServicoTeste {

	@InjectMocks
	private AccountController user;

	@Mock(answer = Answers.RETURNS_SMART_NULLS)
	private UserRepositoryImpl userRepository;
	@InjectMocks
	private AccountRepositoryImpl rw;


	@Test
	public void testerAccount() {
		List<Account> contas = user.getAll();
		assertNotNull(contas);
		assertEquals(6, contas.size());
	}

	@Test
	public void testerCreateAccount() {
		UserModel user = new UserModel();
		user.setEmail("usuario@teste.com");
		Account account = new Account();
		account.setName("Conta de Teste");

		when(userRepository.findByEmail("usuario@teste.com")).thenReturn(Optional.of(user));
		when(rw.save(account));

	}
}

class AccountRepositoryImpl implements AccountRepository {
	@Override
	public Optional<Account> findById(Long id) {
		return Optional.empty();
	}

	@Override
	public void flush() {

	}

	@Override
	public <S extends Account> S saveAndFlush(S entity) {
		return null;
	}

	@Override
	public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<Account> entities) {

	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> integers) {

	}

	@Override
	public void deleteAllInBatch() {

	}

	@Override
	public Account getOne(Integer integer) {
		return null;
	}

	@Override
	public Account getById(Integer integer) {
		return null;
	}

	@Override
	public Account getReferenceById(Integer integer) {
		return null;
	}

	@Override
	public <S extends Account> Optional<S> findOne(Example<S> example) {
		return Optional.empty();
	}

	@Override
	public <S extends Account> List<S> findAll(Example<S> example) {
		return null;
	}

	@Override
	public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
		return null;
	}

	@Override
	public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
		return null;
	}

	@Override
	public <S extends Account> long count(Example<S> example) {
		return 0;
	}

	@Override
	public <S extends Account> boolean exists(Example<S> example) {
		return false;
	}

	@Override
	public <S extends Account, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
		return null;
	}

	@Override
	public <S extends Account> S save(S entity) {
		return null;
	}

	@Override
	public <S extends Account> List<S> saveAll(Iterable<S> entities) {
		return null;
	}

	@Override
	public Optional<Account> findById(Integer integer) {
		return Optional.empty();
	}

	@Override
	public boolean existsById(Integer integer) {
		return false;
	}

	@Override
	public List<Account> findAll() {
		return null;
	}

	@Override
	public List<Account> findAllById(Iterable<Integer> integers) {
		return null;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void deleteById(Integer integer) {

	}

	@Override
	public void delete(Account entity) {

	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> integers) {

	}

	@Override
	public void deleteAll(Iterable<? extends Account> entities) {

	}

	@Override
	public void deleteAll() {

	}

	@Override
	public List<Account> findAll(Sort sort) {
		return null;
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		return null;
	}
	// Implementação dos métodos da interface
}

class UserRepositoryImpl implements UserRepository{
	@Override
	public Optional<UserModel> findByEmail(String email) {
		return Optional.empty();
	}

	@Override
	public UserModel findUserById(Integer userId) {
		return null;
	}

	@Override
	public void flush() {

	}

	@Override
	public <S extends UserModel> S saveAndFlush(S entity) {
		return null;
	}

	@Override
	public <S extends UserModel> List<S> saveAllAndFlush(Iterable<S> entities) {
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<UserModel> entities) {

	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> integers) {

	}

	@Override
	public void deleteAllInBatch() {

	}

	@Override
	public UserModel getOne(Integer integer) {
		return null;
	}

	@Override
	public UserModel getById(Integer integer) {
		return null;
	}

	@Override
	public UserModel getReferenceById(Integer integer) {
		return null;
	}

	@Override
	public <S extends UserModel> List<S> findAll(Example<S> example) {
		return null;
	}

	@Override
	public <S extends UserModel> List<S> findAll(Example<S> example, Sort sort) {
		return null;
	}

	@Override
	public <S extends UserModel> List<S> saveAll(Iterable<S> entities) {
		return null;
	}

	@Override
	public List<UserModel> findAll() {
		return null;
	}

	@Override
	public List<UserModel> findAllById(Iterable<Integer> integers) {
		return null;
	}

	@Override
	public <S extends UserModel> S save(S entity) {
		return null;
	}

	@Override
	public Optional<UserModel> findById(Integer integer) {
		return Optional.empty();
	}

	@Override
	public boolean existsById(Integer integer) {
		return false;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void deleteById(Integer integer) {

	}

	@Override
	public void delete(UserModel entity) {

	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> integers) {

	}

	@Override
	public void deleteAll(Iterable<? extends UserModel> entities) {

	}

	@Override
	public void deleteAll() {

	}

	@Override
	public List<UserModel> findAll(Sort sort) {
		return null;
	}

	@Override
	public Page<UserModel> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public <S extends UserModel> Optional<S> findOne(Example<S> example) {
		return Optional.empty();
	}

	@Override
	public <S extends UserModel> Page<S> findAll(Example<S> example, Pageable pageable) {
		return null;
	}

	@Override
	public <S extends UserModel> long count(Example<S> example) {
		return 0;
	}

	@Override
	public <S extends UserModel> boolean exists(Example<S> example) {
		return false;
	}

	@Override
	public <S extends UserModel, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
		return null;
	}
}