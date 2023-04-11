package com.example.domain.myapp.general.common.base.test;

import javax.inject.Named;

import org.flywaydb.core.Flyway;

/**
 * This class provides methods for handling the database during testing where resets (and other operations) may be
 * necessary.
 */
@Named
public class DbTestHelper {

  private Flyway flyway;

  /**
   * The constructor.
   *
   * @param flyway an instance of type {@link Flyway}.
   */
  public DbTestHelper(Flyway flyway) {
    super();
    this.flyway = flyway;
  }

  /**
   * Drops the whole database.
   */
  public void dropDatabase() {

    this.flyway.clean();
  }

  /**
   * Calls {@link #dropDatabase()} internally, and migrates to the highest available migration (default) or to the
   * {@code migrationVersion} specified by {@link #setMigrationVersion(String)}.
   */
  public void resetDatabase() {

    dropDatabase();
    this.flyway.migrate();
  }

}
