package org.openmetadata.catalog.jdbi3;


import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.openmetadata.catalog.entity.data.Model;

import java.util.List;

public interface ModelDAO3 extends EntityDAO<Model>{
  @Override
  default String getTableName() { return "model_entity"; }

  @Override
  default Class<Model> getEntityClass() { return Model.class; }

  @Override
  default String getNameColumn() { return "fullyQualifiedName"; }

  @Override
  @SqlQuery("SELECT count(*) FROM <table>")
  int listCount(@Define("table") String table);

  @SqlQuery(
          "SELECT json FROM (" +
                  "SELECT fullyQualifiedName, json FROM model_entity WHERE " +
                  "fullyQualifiedName < :before " + // Pagination by model fullyQualifiedName
                  "ORDER BY fullyQualifiedName DESC " +
                  "LIMIT :limit" +
                  ") last_rows_subquery ORDER BY fullyQualifiedName")
  List<String> listBefore(@Bind("limit") int limit,
                          @Bind("before") String before);

  @SqlQuery("SELECT json FROM model_entity WHERE " +
          "fullyQualifiedName > :after " +
          "ORDER BY fullyQualifiedName " +
          "LIMIT :limit")
  List<String> listAfter(@Bind("limit") int limit,
                         @Bind("after") String after);

  @SqlQuery("SELECT count(*) FROM model_entity")
  int listCount();
}
