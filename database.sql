insert into users (bio, email, email_verified, full_name, password, profile_picture, username, website, role_id)
values
    ('', 'banhthibetu@gmail.com', '',N'Bành Thị Bé Tư', '12345', 'index.png', 'banhthibetu', '', 1),
    ('', 'thiTeo@gmail.com', '',N'Lê Thị Tèo', '12345', 'index.png', 'thiTeo', '', 1),
    ('', 'thiNo@gmail.com', '',N'Trần Thị Nở', '12345', 'index.png', 'thiNo', '', 1),
    ('', 'vanGiua@gmail.com', '',N'Trung Văn Giữa', '12345', 'index.png', 'vanGiua', '', 1),
    ('', 'quangLong@gmail.com', '',N'Tèo Quang Long', '12345', 'index.png', 'quangLong', '', 1),
    ('', 'chuoiChien@gmail.com', '',N'Lê Thị Chuối Chiên', '12345', 'index.png', 'chuoiChien', '', 1),
    ('', 'nguyenLongNhan@gmail.com', '',N'Nguyễn Long Nhan', '12345', 'index.png', 'nguyenLongNhan', '', 1),
    ('', 'longThanhNhan@gmail.com', '',N'Long Thanh Nhàn', '12345', 'index.png', 'longThanhNhan', '', 1);

insert into courses (language, price, number_of_students, number_of_ratings, title, course_level_id, created_by)
values
    ('Java',1000000, 100, 100, N'Khóa học lập trình java', 1, 1),
    ('C++',1000000, 100, 100, N'Khóa học lập trình C++', 1, 1),
    ('Ruby',1000000, 100, 100, N'Khóa học lập trình Ruby', 1, 1),
    ('C',1000000, 100, 100, N'Khóa học lập trình C', 1, 1),
    ('Fluter',1000000, 100, 100, N'Khóa học lập trình Fluter', 1, 1),
    ('Java script',1000000, 100, 100,N'Khóa học lập trình Java script', 1, 1),
    ('Web',1000000, 100, 100,N'Khóa học lập trình Web', 1, 1),
    ('Dark',100000, 100, 100,N'Khóa học lâ trình Dark', 1, 1);

insert into course_levels (level_name)
values
    ('max'),
    ('pro max');

insert into sessions (name_sessions, course_id)
values
    ('Bài mở dầu', 1),
    ('Bài hai', 1),
    ('Bài ba', 1),
    ('Bài bốn', 1),
    ('Bài mở dầu', 2),
    ('Bài hai', 3),
    ('Bài ba', 4),
    ('Bài bốn', 5);

insert into lessons (duration, number_of_attachments, title, lesson_type_id, session_id)
values
    (3,10, N'bảo mật cơ bản', 2, 1),
    (3,10, N'Bài tập Cơ bản', 2, 1),
    (3,10, N'Những khó khăn trong môn học', 1, 2),
    (3,10, N'Spring boot 3', 1, 2),
    (3,10, N'Kĩ năng đọc dữ liệu', 3, 3),
    (3,10, N'Phân tích dữ liệu', 1, 3);

insert into comments (comment_text, lessons_id, courses_id, star, user_id)
values
    (N'Khóa học này dở quá anh em ơi', 1, null, 0, 1),
    (N'Thầy giảng khó hiểu quá ta', 1, null, 0, 1),
    (N'Tôi thấy khá là oke', 1, null, 0, 1),
    (N'Ông thầy đẹp trai quá', 1, null, 0, 1),
    (N'Mua khóa học này hết nhiêu tiền nhỉ ?', null, 1, 0, 1),
    (N'Hiện tại đang học nhung tôi chưa hiểu mấy', null, 1, 0, 1),
    (N'Khóa học này là kiến thức cơ bản ấy nhờ', null, 1, 0, 1),
    (N'Tôi có thể học khóa học này lại không nhỉ ?', null, 1, 0, 1),
    (N'Tôi muốn xóa khóa học này như thế nào ?', null, 1, 0, 1),
    (N'Ông thầy giảng cũng được', null, 1, 0, 1),
    (N'Phút thứ năm của video bị lag ấy', null, 1, 0, 1);

insert into course_tags (name_tag, image)
values
    (N'Lập tình nhúng', 'index.png'),
    (N'Phát triển phần mềm', 'index.png'),
    (N'Thiết kế UI/UX', 'index.png'),
    (N'Phát triển web', 'index.png'),
    (N'Lập trình ứng dụng di động', 'index.png'),
    (N'Lập tình game', 'index.png'),
    (N'Bảo mật', 'index.png'),
    (N'Quản lý data', 'index.png');


delete from comments;
delete from roles;
delete from users;
delete from sessions;
delete from lesson_types;
delete from lessons;
delete from course_levels;
delete from courses;

SELECT * FROM cfd.comments where lessons_id = 1;

show create table comments;


select * from course_tags;

alter table courses modify category BIGINT;
SELECT VERSION();

select
    c.id as id,
    u.full_name as fullName,
    u.profile_picture as profilePicture,
    c.comment_text as commentText,
    userCommentParent.full_name as nameUserReply,
    c.comment_id as parentId
from comments c
inner join users u on c.user_id = u.id
left join comments commentParent on c.comment_id = commentParent.id
left join users userCommentParent on commentParent.user_id = userCommentParent.id



