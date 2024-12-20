package com.hao.tmusicmanagement.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("gateway_logs")
@NoArgsConstructor
@AllArgsConstructor
public class LogInfo {
    private String userId;
    private String path;
    private String city;
    private String ip;
    private LocalDateTime time;
}
