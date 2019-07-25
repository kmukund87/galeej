package com.courseHero;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.*;
import java.util.function.Predicate;


/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */
/*
Write a function, that takes in a string, parses it using your existing logic, and adds the Course object to a collection.
Print out the contents of that collection.
Enforce this rule: Once I've added a course, I can't add the same course again, even with a different year/semester.

"ECON-3000 Fall 2019" < Save
"ECON:3000 Fall 2020" < not save, (ECON 3000 already exists)
"ECON-3001 Winter 2020" < save

Print out all courses taken by a user, in order of:
Year (2019->2000)
Semester (Fall->Winter->Spring->Summer)
Department (A-Z)
Course Number(0->999999)

Input:
CS111 2016 Fall
EC112 S2016
CS112 2016 Spring
CS108 W2016
CS121 2019 Fall
MT119 2019 Fall
CS118 2019 Summer

Output:
CS121 2019 Fall
MT119 2019 Fall
CS118 2019 Summer
CS111 2016 Fall
CS108 2016 Winter
CS112 2016 Spring
EC112 2016 Spring


*/
public class Solution {

  public static void main(String[] args) {
    /*System.out.println(tryParse("CS200 Fall 2020"));
    System.out.println(tryParse("CS 200 Fall 2020"));
    System.out.println(tryParse("CS-200 Fall 2020"));
    System.out.println(tryParse("CS:200 Fall 2020"));
    System.out.println(tryParse("CS-200 2020 Fall"));
    System.out.println(tryParse("CS-200 2020 Fall"));
    System.out.println(tryParse("CS-200 2020 F"));
    System.out.println(tryParse("CS200 Fall 2020"));
    System.out.println(tryParse("CS 200 Fall2020"));
    System.out.println(tryParse("CS 200 F20"));
    System.out.println(tryParse("CS 200 20F"));

    System.out.println();
    System.out.println("Invalid cases:");
    System.out.println(tryParse(""));
    System.out.println(tryParse("CS   200 Fall 2020"));
    System.out.println(tryParse("200 CS 200 Fall 2020"));
    System.out.println(tryParse("CS Fall 2020"));
    System.out.println(tryParse("CS 200A Fall 2020"));
    System.out.println(tryParse("CS 200A Autumn 2020"));
    System.out.println(tryParse("CS 200A Fa 2020"));
    System.out.println(tryParse("CS 200A Fall:2020"));
    System.out.println(tryParse("CS 200A Fall 2020AD"));
    System.out.println(tryParse("CS 200A Fall 202000"));*/


    System.out.println(parseAll(
        "CS111 2016 Fall",
        "EC112 S2016",
        "CS112 2016 Spring",
        "CS108 W2016",
        "CS121 2019 Fall",
        "MT119 2019 Fall",
        "CS118 2019 Summer"));
  }

  public static Collection<Course> parseAll(String... courseDescriptors) {
    Map<String, Course> courses = new HashMap<>();

    for(String courseDescriptor : courseDescriptors) {
      Course course = tryParse(courseDescriptor);
      if (course != null) {
        courses.put(course.getCourseKey(), course);
      }
    }

    List<Course> sortedCourses = new ArrayList<>(courses.values());
    Collections.sort(sortedCourses);

    return sortedCourses;
  }

  static class Course implements Comparable<Course> {
    String _department;
    int _courseNumber;
    Semester _semester;
    int _year;

    Course(String department, int courseNumber, Semester semester, int year) {
      _department = department;
      _courseNumber = courseNumber;
      _semester = semester;
      _year = year < 100 ? year + 2000 : year;
    }

    @Override
    public String toString() {
      return String.join(" ", _department, String.valueOf(_courseNumber), String.valueOf(_semester), String.valueOf(_year));
    }

    String getCourseKey() {
      return _department + _courseNumber;
    }

    @Override
    public int compareTo(Course that) {
      if (this._year !=that._year) {
        return that._year - this._year;
      }
      if (this._semester != that._semester) {
        return this._semester.compareTo(that._semester);
      }
      if (!this._department.equals(that._department)) {
        return this._department.compareTo(that._department);
      }
      if (this._courseNumber != that._courseNumber) {
        return this._courseNumber - that._courseNumber;
      }

      return 0;
    }
  }

  interface Matcher<T> {
    boolean match(char c);
    boolean isValid();
    T getValue();
  }

  static class StringMatcher implements Matcher<String> {
    StringBuilder _value = new StringBuilder();

    public boolean match(char c) {
      if (Character.isAlphabetic(c)) {
        _value.append(c);
        return true;
      }
      return false;
    }

    public boolean isValid() {
      return _value.length() > 0;
    }

    public String getValue() {
      return _value.toString();
    }
  }

  static class CharacterMatcher implements Matcher<Character>{
    boolean _state = false;
    Character _matchedCharacter;
    Set<Character> _validMatches;

    CharacterMatcher(Character... validMatches) {
      _validMatches = new HashSet<>(Arrays.asList(validMatches));
    }

    public boolean match(char c) {
      if (!_state && _validMatches.contains(c)) {
        _matchedCharacter = c;
        _state = true;
        return true;
      }
      return false;
    }

    // Character.MIN_VALUE matches empty string too. This is used when a delimiter need not be explicitly present
    public boolean isValid() {
      return _validMatches.contains(Character.MIN_VALUE) || _state;
    }

    public Character getValue() {
      return _matchedCharacter;
    }
  }

  static class NumberMatcher implements Matcher<Integer> {
    int _value = 0;
    Predicate<Integer> _validator;

    NumberMatcher() {
    }

    NumberMatcher(Predicate<Integer> validator) {
      _validator = validator;
    }

    public boolean match(char c) {
      if (Character.isDigit(c)) {
        _value = _value * 10 + Character.getNumericValue(c);
        return true;
      }
      return false;
    }

    public boolean isValid() {
      return _validator == null ? _value > 0  : _validator.test(_value);
    }

    public Integer getValue() {
      return _value;
    }
  }

  enum Semester {
    Fall("F"),
    Winter("W"),
    Spring("S"),
    Summer("Su");

    public final Set<String> _match;

    Semester(String alternative) {
      _match = new HashSet<>();
      _match.add(this.toString());
      _match.add(alternative);
    }
  }

  static class TrieNode<T> {
    Map<Character, TrieNode<T>> _branches = new HashMap<>();
    T _value;

    TrieNode() {
    }

    void add(String s, int index, T value) {
      if (index == s.length()) {
        _value = value;
      } else {
        _branches.computeIfAbsent(s.charAt(index), c -> new TrieNode<T>());
        _branches.get(s.charAt(index)).add(s, index + 1, value);
      }
    }
  }

  private static final TrieNode<Semester> _semesterTrieNode = new TrieNode<>();

  static {
    for (Semester s : Semester.values()) {
      for (String abbr : s._match) {
        _semesterTrieNode.add(abbr, 0, s);
      }
    }
  }

  static class TrieMatcher<T> implements Matcher<T>{
    TrieNode<T> _state;

    TrieMatcher(TrieNode<T> trieStates) {
      _state = trieStates;
    }

    public boolean match(char c) {
      if (_state._branches.containsKey(c)) {
        _state = _state._branches.get(c);
        return true;
      }
      return false;
    }

    public boolean isValid() {
      return _state._value != null;
    }

    public T getValue() {
      return _state._value;
    }
  }

  static class State {
    String _value;
    int _index;

    State(String value, int index) {
      _value = value;
      _index = index;
    }

    private boolean matchPattern(Matcher<?> pattern) {
      for (; _index < _value.length(); _index++) {
        char c = _value.charAt(_index);
        if (!pattern.match(c)) {
          break;
        }
      }
      return pattern.isValid();
    }
  }

  public static Course tryParse(String course) {
    Matcher<String> department = new StringMatcher();
    Matcher<Character> departmentDelimiter = new CharacterMatcher(':', '-', ' ', Character.MIN_VALUE);
    Matcher<Integer> courseNumber = new NumberMatcher();
    Matcher<Character> courseDelimiter = new CharacterMatcher(' ');
    Matcher<Integer> year = new NumberMatcher(y -> (y > 0 && y < 100) || (y > 999 && y < 10000));
    Matcher<Character> yearDelimiter = new CharacterMatcher(' ', Character.MIN_VALUE);
    Matcher<Semester> semester = new TrieMatcher<>(_semesterTrieNode);

    State state = new State(course, 0);
    boolean valid =
      state.matchPattern(department)
      && state.matchPattern(departmentDelimiter)
      && state.matchPattern(courseNumber)
      && state.matchPattern(courseDelimiter)
      && (
          state.matchPattern(year)
          && state.matchPattern(yearDelimiter)
          && state.matchPattern(semester)
        ||
          state.matchPattern(semester)
          && state.matchPattern(yearDelimiter)
          && state.matchPattern(year)
      );

    return valid
        ? new Course(
        department.getValue(),
        courseNumber.getValue(),
        semester.getValue(),
        year.getValue())
        : null;
  }
}

