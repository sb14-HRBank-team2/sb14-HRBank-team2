package com.sprint.hrbank.application.backup.provided.command;

import java.nio.file.Path;

public interface LogCreator {

  Path createLog(String worker, Exception exception);
}
