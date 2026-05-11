package com.mycompany.semaforostopologia;

import java.util.List;

public interface SemaforoDao {

    List<SemaforoEntity> listAll();

    SemaforoEntity findById(String id);

    void persist(SemaforoEntity entity);
}
