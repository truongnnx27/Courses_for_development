package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.OptionDto;
import com.example.coursefordevelopment.dto.QuestionDto;
import com.example.coursefordevelopment.dto.QuizDto;
import com.example.coursefordevelopment.dto.Request.AppException;
import com.example.coursefordevelopment.entity.*;
import com.example.coursefordevelopment.exception.ErrorCode;
import com.example.coursefordevelopment.mapstruct.OptionMapper;
import com.example.coursefordevelopment.mapstruct.QuestionMapper;
import com.example.coursefordevelopment.mapstruct.QuestionTypeMapper;
import com.example.coursefordevelopment.mapstruct.QuizMapper;
import com.example.coursefordevelopment.reponsitory.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizzService {

    //Repository
    QuizRepository quizRepository;
    QuestionTypeRepository questionTypeRepository;
    QuestionRepository questionRepository;
    OptionRepository optionRepository;
    LessonRepository lessonRepository;

    //Mapper
    QuizMapper quizMapper = QuizMapper.INSTANCE;
    QuestionMapper questionMapper = QuestionMapper.INSTANCE;
    OptionMapper optionMapper= OptionMapper.INSTANCE;
    QuestionTypeMapper questionTypeMapper= QuestionTypeMapper.INSTANCE;

    @Transactional
    public QuizDto createQuiz(QuizDto quizDto) {
        // 1. Kiểm tra Lesson tồn tại
        Lesson lesson = lessonRepository.findById(quizDto.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        // 2. Lưu Quiz với Lesson đã tìm được
        Quiz quiz = quizMapper.quizDtoToQuiz(quizDto);
        quiz.setLesson(lesson);  // Gắn Lesson vào Quiz
        quiz = quizRepository.save(quiz);  // Lưu Quiz và nhận lại đối tượng với ID

        // 3. Kiểm tra và lưu các Question liên quan đến Quiz
        if (quizDto.getQuestions() != null && !quizDto.getQuestions().isEmpty()) {  // Kiểm tra danh sách câu hỏi
            for (QuestionDto questionDto : quizDto.getQuestions()) {
                // 3.1 Kiểm tra QuestionType tồn tại
                QuestionType questionType = questionTypeRepository.findById(questionDto.getQuestionTypeId())
                        .orElseThrow(() -> new AppException(ErrorCode.QUESTION_TYPE_NOT_FOUND));

                // 3.2 Lưu Question với QuestionType và Quiz đã tìm được
                Question question = questionMapper.questionDtoToQuestion(questionDto);
                question.setQuiz(quiz);  // Gắn Quiz vào Question
                question.setQuestionType(questionType);  // Gắn QuestionType vào Question
                question = questionRepository.save(question);  // Lưu Question và nhận lại đối tượng với ID

                // 4. Lưu các Option liên quan đến Question
                if (questionDto.getOptions() != null && !questionDto.getOptions().isEmpty()) {
                    for (OptionDto optionDto : questionDto.getOptions()) {
                        Option option = optionMapper.optionDtoToOption(optionDto);
                        option.setQuestion(question);  // Gắn Question đã lưu vào Option
                        optionRepository.save(option);  // Lưu Option vào cơ sở dữ liệu
                    }
                }
            }
        }

        // Trả về Quiz đã lưu kèm theo các Question và Option
        return quizMapper.quizToQuizDto(quiz);
    }

    @Transactional
    public QuizDto create(QuizDto quizDto) {
        // 1. Lưu Quiz trước
        // Tìm lesson theo ID
        Lesson lesson = lessonRepository.findById(quizDto.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        // Chuyển đổi quizDto thành quiz entity và gán lesson cho quiz
        Quiz quiz = quizMapper.quizDtoToQuiz(quizDto);
        quiz.setLesson(lesson);

        // Lưu quiz và nhận lại quiz đã lưu (đã có ID)
        Quiz createdQuiz = quizRepository.save(quiz);

        // 2. Lưu các Question liên kết với Quiz
        List<Question> savedQuestions = quizDto.getQuestions().stream()
                .map(questionDto -> {
                    // Chuyển đổi questionDto thành question entity
                    Question question = questionMapper.questionDtoToQuestion(questionDto);
                    question.setQuiz(createdQuiz);  // Gán quiz đã lưu cho question

                    // Tìm loại câu hỏi (QuestionType)
                    QuestionType questionType = questionTypeRepository.findById(questionDto.getQuestionTypeId())
                            .orElseThrow(() -> new AppException(ErrorCode.QUESTION_TYPE_NOT_FOUND));
                    question.setQuestionType(questionType);  // Gán loại câu hỏi

                    // Lưu question và nhận lại question đã lưu (đã có ID)
                    Question savedQuestion = questionRepository.save(question);

                    // 3. Lưu các Option liên kết với Question
                    List<Option> options =(questionDto.getOptions())
                            .stream()
                            .map(optionDTO -> {
                                // Chuyển đổi optionDTO thành option entity
                                Option option = optionMapper.optionDtoToOption(optionDTO);
                                option.setQuestion(savedQuestion);  // Gán question đã lưu cho option
                                return option;
                            }).collect(Collectors.toList());

                    // Lưu các Option vào database
                    optionRepository.saveAll(options);

                    return savedQuestion;  // Trả về question đã lưu
                }).collect(Collectors.toList());

        // 4. Chuyển đổi quiz đã lưu thành quizDto để trả về
        QuizDto savedQuizDto = quizMapper.quizToQuizDto(createdQuiz);
        savedQuizDto.setQuestions(
                savedQuestions.stream()
                        .map(questionMapper::questionToQuestionDto)
                        .collect(Collectors.toList())
        );

        return savedQuizDto;
    }







}
