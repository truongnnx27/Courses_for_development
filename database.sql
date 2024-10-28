create database cfd;

INSERT INTO users(id, avatar_url, birthday, created_date, email, fullname, gender, is_active, password, phone, updated_date, username, version, role_id)
VALUES
    ('quockhanh123', 'index.png', '2004-02-01', '2023-01-10', 'quockhanh123@example.com', 'Quốc Khánh', 'MALE', 1, 'password123', '0901234567', '2023-01-15', 'quockhanh', 1, 3),
    ('user001', 'avatar1.png', '1990-05-12', '2023-01-10', 'user001@example.com', 'Nguyễn Văn A', 'MALE', 1, 'password123', '0901123456', '2023-01-15', 'user001', 1, 1),
    ('user002', 'avatar2.png', '1985-09-23', '2023-02-20', 'user002@example.com', 'Trần Thị B', 'FEMALE', 1, 'password456', '0902234567', '2023-02-25', 'user002', 1, 2),
    ('user003', 'avatar3.png', '1992-11-07', '2023-03-05', 'user003@example.com', 'Lê Văn C', 'PRIVATE', 1, 'password789', '0903345678', '2023-03-10', 'user003', 1, 1),
    ('user004', 'avatar4.png', '1996-06-15', '2023-04-12', 'user004@example.com', 'Phạm Thị D', 'FEMALE', 1, 'passwordabc', '0904456789', '2023-04-18', 'user004', 1, 2),
    ('user005', 'avatar5.png', '2001-01-25', '2023-05-10', 'user005@example.com', 'Hoàng Văn E', 'MALE', 1, 'passworddef', '0905567890', '2023-05-15', 'user005', 1, 1),
    ('user006', 'avatar6.png', '1999-04-30', '2023-06-08', 'user006@example.com', 'Lý Thị F', 'FEMALE', 1, 'passwordghi', '0906678901', '2023-06-13', 'user006', 1, 2),
    ('user007', 'avatar7.png', '1988-03-11', '2023-07-07', 'user007@example.com', 'Ngô Văn G', 'MALE', 1, 'passwordjkl', '0907789012', '2023-07-12', 'user007', 1, 1),
    ('user008', 'avatar8.png', '1995-10-21', '2023-08-15', 'user008@example.com', 'Đặng Thị H', 'FEMALE', 1, 'passwordmno', '0908890123', '2023-08-20', 'user008', 1, 2),
    ('user009', 'avatar9.png', '2003-12-19', '2023-09-14', 'user009@example.com', 'Vũ Văn I', 'PRIVATE', 1, 'passwordpqr', '0909901234', '2023-09-18', 'user009', 1, 1);

INSERT INTO categories (category_name)
VALUES
    ('Programming'),
    ('Data Science'),
    ('Web Development'),
    ('Cloud Computing'),
    ('Cybersecurity'),
    ('Mobile Development'),
    ('AI and Robotics'),
    ('Blockchain'),
    ('Digital Marketing'),
    ('Game Development'),
    ('Machine Learning'),
    ('Graphic Design'),
    ('Ethical Hacking'),
    ('Big Data'),
    ('Content Writing'),
    ('Python Programming'),
    ('SEO'),
    ('Data Analysis'),
    ('Product Management'),
    ('UI/UX Design');

select
    c.id as id,
    u.id as idUserComment,
    u.fullname as fullName,
    u.avatar_url as profilePicture,
    c.comment_text as commentText,
    userCommentParent.fullname as nameUserReply,
    c.comment_id as parentId
from comments c
inner join users u on c.user_id = u.id
left join comments commentParent on c.comment_id = commentParent.id
left join users userCommentParent on commentParent.user_id = userCommentParent.id



