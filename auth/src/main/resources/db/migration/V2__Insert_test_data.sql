INSERT INTO users (username, password, email, first_name, last_name, role)
VALUES
('admin', '$2a$10$xVCH4IA5wJhOoFd3Q1HrA.D0YvJjRgN5JtT7kZzUJkXK9z1YbL2W', 'papichmemas@gmail.com', 'Иван', 'Петров', 'ADMIN'),
('user1', '$2a$10$xVCH4IA5wJhOoFd3Q1HrA.D0YvJjRgN5JtT7kZzUJkXK9z1YbL2W', 'user1@example.com', 'Мария', 'Сидорова', 'USER'),
('user2', '$2a$10$xVCH4IA5wJhOoFd3Q1HrA.D0YvJjRgN5JtT7kZzUJkXK9z1YbL2W', 'user2@example.com', 'Алексей', 'Иванов', 'USER'),
('manager', '$2a$10$xVCH4IA5wJhOoFd3Q1HrA.D0YvJjRgN5JtT7kZzUJkXK9z1YbL2W', 'manager@example.com', 'Ольга', 'Кузнецова', 'USER');