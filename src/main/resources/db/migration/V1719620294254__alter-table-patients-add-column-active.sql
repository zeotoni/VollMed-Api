ALTER TABLE patients ADD active TINYINT;
UPDATE patients SET active = 1;