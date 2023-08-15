package com.skypro.animalshelter.service;

import com.skypro.animalshelter.exception.ReportNotFoundException;
import com.skypro.animalshelter.model.Report;
import com.skypro.animalshelter.model.ShelterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import com.skypro.animalshelter.exception.ShelterUserNotFoundException;

import java.util.List;

public interface ShelterUserService {

    /**
     * Вывод списка всех пользователей из базы данных <br>
     * @see JpaRepository#findAll()
     * @return список объектов класса {@link ShelterUser}
     */
    List<ShelterUser> getAllUsers();

    /**
     * Создание нового пользователя и сохранение его в базу данных <br>
     * @see JpaRepository#save(Object)
     * @param shelterUser объект класса {@link ShelterUser}, не может быть {@code null}
     * @return созданный объект класса {@link ShelterUser}
     */
    ShelterUser createUser(ShelterUser shelterUser);

    /**
     * Поиск пользователя в базе данных по его {@code id} <br>
     * @see JpaRepository#findById(Object)
     * @param id идентификатор искомого пользователя, не может быть {@code null}
     * @throws ShelterUserNotFoundException если пользователь с указанным {@code id} не был найден в базе данных
     * @return найденный объект класса {@link ShelterUser}
     */
    ShelterUser findUserById(Long id);

    /**
     * Редактирование параметров пользователя и сохранение его в базу данных <br>
     * @see  JpaRepository#save(Object)
     * @param shelterUser объект класса {@link ShelterUser}, не может быть {@code null}
     * @return отредактированный объект класса {@link ShelterUser}
     * @throws ShelterUserNotFoundException если пользователь не был найден в базе данных
     */
    ShelterUser editUser(ShelterUser shelterUser);

    /**
     * Удаление пользователя из базы данных по его {@code id} <br>
     * @see JpaRepository#deleteById(Object)
     * @param id идентификатор пользователя, которого нужно удалить из базы данных, не может быть {@code null}
     * @throws ShelterUserNotFoundException если пользователь с указанным {@code id} не был найден в базе данных
     * @return {@code true} если объект был найден в базе данных, в противном случае {@link ShelterUserNotFoundException}
     */
    boolean deleteUserById(Long id);
}
