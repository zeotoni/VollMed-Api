INSERT INTO users (id, login, password)
VALUES (1, 'email@voll.med', '$2a$12$VeZaOCvaQuySx.HJ.0X1m.DMQkR/H7Bd9MyeUUwLDnnAuKkPfUEhK')
ON DUPLICATE KEY UPDATE 
login = VALUES(login),
password = VALUES(password);