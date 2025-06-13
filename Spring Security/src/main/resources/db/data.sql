-- 插入图书分类
INSERT INTO category (name, description) VALUES 
('文学', '文学类书籍'),
('科技', '科学技术类书籍'),
('历史', '历史类书籍'),
('艺术', '艺术类书籍');

-- 插入示例图书
INSERT INTO book (title, author, publisher, publish_date, isbn, description, content) VALUES 
('Spring Security实战', '王松', '电子工业出版社', '2023-01-01', '9787121431234', 
'Spring Security实战指南，详细讲解Spring Security的使用方法和最佳实践。',
'Spring Security是一个功能强大且高度可定制的身份验证和访问控制框架...');

-- 为图书分配分类
INSERT INTO book_category (book_id, category_id)
SELECT b.id, c.id
FROM book b, category c
WHERE b.title = 'Spring Security实战' AND c.name = '科技'; 