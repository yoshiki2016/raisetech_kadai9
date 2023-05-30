package com.example.kadai9;

import java.util.List;

public interface NameService {
    List<Name> findAll();
    Name findName(int id);
}
