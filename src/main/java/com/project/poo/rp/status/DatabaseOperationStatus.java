package com.project.poo.rp.status;

import com.project.poo.rp.enumerations.DbOperationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatabaseOperationStatus {
    DbOperationStatus status;
    Object data;
    String message;
}
