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

import java.util.ArrayList;
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
    public QuizDto createQuizz(QuizDto quizDto) {
        // 1. Lưu Quiz trước
        Lesson lesson = lessonRepository.findById(quizDto.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        Quiz quiz = quizMapper.quizDtoToQuiz(quizDto);
        quiz.setLesson(lesson);

        // Lưu quiz và flush để lấy ID ngay lập tức
        Quiz createdQuiz = quizRepository.saveAndFlush(quiz);

        // 2. Lưu các Question liên kết với Quiz
        List<Question> savedQuestions = new ArrayList<>();
        for (QuestionDto questionDto : quizDto.getQuestions()) {
            // Lấy questionType cho từng câu hỏi
            QuestionType questionType = questionTypeRepository.findById(questionDto.getQuestionTypeId())
                    .orElseThrow(() -> new AppException(ErrorCode.LESSON_Type_NOT_FOUND));

            // Chuyển đổi questionDto thành question entity
            Question question = questionMapper.questionDtoToQuestion(questionDto);
            question.setQuestionType(questionType);
            question.setQuiz(createdQuiz);

            // Lưu question
            question = questionRepository.save(question);

            // 3. Lưu các Option liên kết với Question
            List<Option> options = new ArrayList<>();
            for (OptionDto optionDTO : questionDto.getOptions()) {
                Option option = optionMapper.optionDtoToOption(optionDTO);
                option.setQuestion(question);
                options.add(option);
            }


            optionRepository.saveAll(options);

            // Thêm question đã lưu vào danh sách savedQuestions
            savedQuestions.add(question);

            // In thông tin để kiểm tra (nếu cần)
            System.out.println("Quiz ID: " + createdQuiz.getId());
            System.out.println("Question ID: " + question.getId());
            System.out.println("ID Option:"+options.get(0).getId());
        }

        // 4. Chuyển đổi quiz đã lưu thành quizDto để trả về
        QuizDto savedQuizDto = quizMapper.quizToQuizDto(createdQuiz);
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question savedQuestion : savedQuestions) {
            QuestionDto questionDto = questionMapper.questionToQuestionDto(savedQuestion);

            List<OptionDto> optionDtos = new ArrayList<>();
            for (Option option : savedQuestion.getOptions()) {
                optionDtos.add(optionMapper.optionToOptionDto(option));
            }
            questionDto.setOptions(optionDtos);

            questionDtos.add(questionDto);
        }
        savedQuizDto.setQuestions(questionDtos);

        return savedQuizDto;
    }




    @Transactional
    public QuizDto create(QuizDto quizDto) {
        // 1. Lưu Quiz trước
        Lesson lesson = lessonRepository.findById(quizDto.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        Quiz quiz = quizMapper.quizDtoToQuiz(quizDto);
        quiz.setLesson(lesson);

        // Lưu quiz và flush để lấy ID ngay lập tức
        Quiz createdQuiz = quizRepository.save(quiz);

        // 2. Lưu các Question liên kết với Quiz
        List<Question> savedQuestions = quizDto.getQuestions().stream()
                .map(questionDto -> {
                    // Lấy questionType cho từng câu hỏi
                    QuestionType questionType = questionTypeRepository.findById(questionDto.getQuestionTypeId())
                            .orElseThrow(() -> new AppException(ErrorCode.LESSON_Type_NOT_FOUND));

                    // Chuyển đổi questionDto thành question entity
                    Question question = questionMapper.questionDtoToQuestion(questionDto);
                    question.setQuestionType(questionType); // Gán questionType đã lưu cho question
                    question.setQuiz(createdQuiz); // Gán quiz đã lưu cho question

                    // Lưu question và flush để lấy ID ngay lập tức
                    Question savedQuestion = questionRepository.save(question);

                    // 3. Lưu các Option liên kết với Question
                    List<Option> options = questionDto.getOptions().stream()
                            .map(optionDTO -> {
                                Option option = optionMapper.optionDtoToOption(optionDTO);
                                option.setQuestion(savedQuestion); // Gán question đã lưu cho option
                                return option;
                            }).collect(Collectors.toList());

                    // Lưu các Option vào database
                    optionRepository.saveAll(options);

                    System.out.println("Quiz ID: " + createdQuiz.getId());
                    System.out.println("Question ID: " + savedQuestion.getId());


                    return savedQuestion; // Trả về question đã lưu

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
