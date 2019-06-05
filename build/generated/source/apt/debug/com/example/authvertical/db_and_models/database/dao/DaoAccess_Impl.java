package com.example.authvertical.db_and_models.database.dao;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.example.authvertical.db_and_models.database.type_converters.UserTypeConverter;
import com.example.authvertical.db_and_models.login_entity.LoginEntity;
import com.example.authvertical.db_and_models.login_entity.User;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unchecked")
public class DaoAccess_Impl implements DaoAccess {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfLoginEntity;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfLoginEntity;

  public DaoAccess_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLoginEntity = new EntityInsertionAdapter<LoginEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `login`(`email_address`,`token`,`expirate`,`role`,`user_role`,`user`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LoginEntity value) {
        if (value.getEmail_address() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getEmail_address());
        }
        if (value.getToken() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getToken());
        }
        stmt.bindLong(3, value.getExpirate());
        if (value.getRole() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getRole());
        }
        if (value.getUser_role() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getUser_role());
        }
        final String _tmp;
        _tmp = UserTypeConverter.fromRoc(value.getUser());
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp);
        }
      }
    };
    this.__updateAdapterOfLoginEntity = new EntityDeletionOrUpdateAdapter<LoginEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `login` SET `email_address` = ?,`token` = ?,`expirate` = ?,`role` = ?,`user_role` = ?,`user` = ? WHERE `email_address` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LoginEntity value) {
        if (value.getEmail_address() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getEmail_address());
        }
        if (value.getToken() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getToken());
        }
        stmt.bindLong(3, value.getExpirate());
        if (value.getRole() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getRole());
        }
        if (value.getUser_role() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getUser_role());
        }
        final String _tmp;
        _tmp = UserTypeConverter.fromRoc(value.getUser());
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp);
        }
        if (value.getEmail_address() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getEmail_address());
        }
      }
    };
  }

  @Override
  public long storeUser(LoginEntity loginEntity) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfLoginEntity.insertAndReturnId(loginEntity);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(LoginEntity loginEntity) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfLoginEntity.handle(loginEntity);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LoginEntity getUser(String email_address) {
    final String _sql = "Select * from login where email_address==?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email_address == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email_address);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfEmailAddress = _cursor.getColumnIndexOrThrow("email_address");
      final int _cursorIndexOfToken = _cursor.getColumnIndexOrThrow("token");
      final int _cursorIndexOfExpirate = _cursor.getColumnIndexOrThrow("expirate");
      final int _cursorIndexOfRole = _cursor.getColumnIndexOrThrow("role");
      final int _cursorIndexOfUserRole = _cursor.getColumnIndexOrThrow("user_role");
      final int _cursorIndexOfUser = _cursor.getColumnIndexOrThrow("user");
      final LoginEntity _result;
      if(_cursor.moveToFirst()) {
        _result = new LoginEntity();
        final String _tmpEmail_address;
        _tmpEmail_address = _cursor.getString(_cursorIndexOfEmailAddress);
        _result.setEmail_address(_tmpEmail_address);
        final String _tmpToken;
        _tmpToken = _cursor.getString(_cursorIndexOfToken);
        _result.setToken(_tmpToken);
        final long _tmpExpirate;
        _tmpExpirate = _cursor.getLong(_cursorIndexOfExpirate);
        _result.setExpirate(_tmpExpirate);
        final String _tmpRole;
        _tmpRole = _cursor.getString(_cursorIndexOfRole);
        _result.setRole(_tmpRole);
        final String _tmpUser_role;
        _tmpUser_role = _cursor.getString(_cursorIndexOfUserRole);
        _result.setUser_role(_tmpUser_role);
        final User _tmpUser;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfUser);
        _tmpUser = UserTypeConverter.toGeometry(_tmp);
        _result.setUser(_tmpUser);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
