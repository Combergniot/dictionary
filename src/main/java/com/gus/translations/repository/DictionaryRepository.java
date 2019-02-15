package com.gus.translations.repository;

import com.gus.translations.model.Dictionary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DictionaryRepository extends CrudRepository<Dictionary, Long> {


}
