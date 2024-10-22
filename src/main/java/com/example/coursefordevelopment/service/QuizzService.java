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
        // 1. Retrieve the lesson first
        Lesson lesson = lessonRepository.findById(quizDto.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        // 2. Map the DTO to the Quiz entity
        Quiz quiz = quizMapper.quizDtoToQuiz(quizDto);
        quiz.setLesson(lesson);

        // 3. Iterate through the questions in the QuizDto and set relationships
        List<Question> questions = new ArrayList<>();
        for (QuestionDto questionDto : quizDto.getQuestions()) {

            QuestionType questionType = questionTypeRepository.findById(questionDto.getQuestionTypeId())
                    .orElseThrow(() -> new AppException(ErrorCode.QUESTION_TYPE_NOT_FOUND));

            Question question = questionMapper.questionDtoToQuestion(questionDto);
            question.setQuestionType(questionType);
            question.setQuiz(quiz);

            // 4. Set the options for each question
            List<Option> options = new ArrayList<>();
            for (OptionDto optionDto : questionDto.getOptions()) {
                Option option = optionMapper.optionDtoToOption(optionDto);
                option.setQuestion(question);
                options.add(option);
            }

            question.setOptions(options);

            // Add question to the quiz
            questions.add(question);
        }
        // Set the questions in the quiz entity
        quiz.setQuestions(questions);

        // 5. Save the quiz with cascade, which will save the questions and options together
        Quiz savedQuiz = quizRepository.save(quiz);

        // 6. Convert the saved Quiz back to QuizDto
        QuizDto savedQuizDto = quizMapper.quizToQuizDto(savedQuiz);
        return savedQuizDto;
    }


    //create Quiz,question and option 1 cách
    @Transactional
    public QuizDto create(QuizDto quizDto)
    {
        // 1. Lưu Quiz trước
        Lesson lesson = lessonRepository.findById(quizDto.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        Quiz quiz = quizMapper.quizDtoToQuiz(quizDto);
        quiz.setLesson(lesson);

        Quiz createdQuiz = quizRepository.save(quiz);

        // 2. Lưu các Question liên kết với Quiz
        List<Question> savedQuestions = quizDto.getQuestions().stream()
                .map(questionDto -> {

                    QuestionType questionType = questionTypeRepository.findById(questionDto.getQuestionTypeId())
                            .orElseThrow(() -> new AppException(ErrorCode.LESSON_Type_NOT_FOUND));

                    Question question = questionMapper.questionDtoToQuestion(questionDto);
                    question.setQuestionType(questionType);
                    question.setQuiz(createdQuiz);

                    Question savedQuestion = questionRepository.save(question);

                    // 3. Lưu các Option liên kết với Question
                    List<Option> options = questionDto.getOptions().stream()
                            .map(optionDTO -> {
                                Option option = optionMapper.optionDtoToOption(optionDTO);
                                option.setQuestion(savedQuestion);
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

    //update Quiz
    public QuizDto updateQuiz(QuizDto quizDto, Long id)
    {
        Quiz existingQuiz = quizRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.LESSON_NOT_FOUND));

        existingQuiz.setTitle(quizDto.getTitle());
        existingQuiz.setLesson(lessonRepository.findById(quizDto.getLessonId())
                .orElseThrow(()-> new AppException(ErrorCode.LESSON_NOT_FOUND)));

        return quizMapper.quizToQuizDto(existingQuiz);
    }

    //getByIdQuiz
    public QuizDto getQuizById(Long id)
    {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.LESSON_NOT_FOUND));

        return quizMapper.quizToQuizDto(quiz);
    }

    //get question by quiz
    public List<QuestionDto> getQuestionByQuiz(Long id)
    {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.QUIZ_NOT_FOUND));

        List<Question> questions= quiz.getQuestions();
        return questions.stream()
                .map(questionMapper::questionToQuestionDto)
                .collect(Collectors.toList());
    }

    //delete quizz
    public void deleteQuiz(Long id)
    {
        Quiz  quiz = quizRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.QUIZ_NOT_FOUND));

        List<Question> questions = questionRepository.findAllByQuiz(quiz);

        for (Question question: questions)
        {
            List<Option> options = optionRepository.findAllByQuestionId(question.getId());

            optionRepository.deleteAll(options);
        }
        questionRepository.deleteAll(questions);

        quizRepository.delete(quiz);
    }
}
