package com.rizalpurnama.dao;

import com.rizalpurnama.entity.Kelurahan;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface KelurahanDao extends PagingAndSortingRepository<Kelurahan, String> {
}
