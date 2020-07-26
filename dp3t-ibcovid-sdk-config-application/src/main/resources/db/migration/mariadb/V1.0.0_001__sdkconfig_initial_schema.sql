CREATE TABLE t_sdk_config (
   id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
   name VARCHAR(100) NOT NULL,
   value TEXT NOT NULL,
   created_at TIMESTAMP NOT NULL DEFAULT now(),
   modified_at TIMESTAMP
);

CREATE UNIQUE INDEX t_sdk_config_name_uk ON t_sdk_config (name);

INSERT INTO t_sdk_config (id, name, value)
 VALUES (1, 'BACKEND_SDK_CONFIG', '{"forceUpdate":false,"forceTraceShutdown":false,"infoBox":null,"sdkConfig":{"numberOfWindowsForExposure":3,"eventThreshold":0.8,"badAttenuationThreshold":73.0,"contactAttenuationThreshold":73.0},"iosGaenSdkConfig":{"lowerThreshold":50,"higherThreshold":55,"factorLow":1.0,"factorHigh":0.5,"triggerThreshold":15},"androidGaenSdkConfig":{"lowerThreshold":50,"higherThreshold":55,"factorLow":1.0,"factorHigh":0.5,"triggerThreshold":15}}');
