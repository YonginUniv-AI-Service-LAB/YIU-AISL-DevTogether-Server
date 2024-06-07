package yiu.aisl.devTogether.domain.state;

import lombok.Getter;


@Getter
public enum SubjectCategory {
    C(1),
    Cpp(2),
    Java(3),
    JavaScript(4),
    Python(5),
    Ruby(6),
    Go(7),
    Csharp(8),
    Swift(9),
    Rust(10),
    Kotlin(11),
    PHP(12),
    TypeScript(13),
    HTML(14),
    CSS(15),
    Scala(16),
    Haskell(17),
    Objective_C(18),
    Perl(19),
    Lua(20),
    Shell(21),
    R(22),
    Dart(23),
    Assembly(24),
    Visual_Basic(25),
    Fsharp(26),
    Clojure(27),
    Erlang(28),
    Fortran(29),
    Julia(30),
    MATLAB(31),
    Groovy(32),
    PL_SQL(33),
    SQL(34),
    Prolog(35),
    Ada(36),
    Cobol(37),
    Pascal(38),
    Lisp(39),
    Scheme(40),
    Tcl(41),
    ActionScript(42),
    Delphi(43),
    PowerShell(44),
    Batch(45),
    Arduino(46),
    Vhdl(47),
    Verilog(48),
    Java_Spring(49);




    private final int value;

    SubjectCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SubjectCategory fromInt(int value) {
        for (SubjectCategory subject : SubjectCategory.values()) {
            if (subject.getValue() == value) {
                return subject;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }
}
