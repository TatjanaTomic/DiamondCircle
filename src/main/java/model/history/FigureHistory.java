package model.history;

import model.figure.FigureColor;

import java.io.Serializable;
import java.util.List;

public class FigureHistory implements Serializable {
    private static final String FIGURE = "Figura ";
    private static final String CROSSED_PATH = "predjeni put ";
    private static final String REACHED_TO_END = "stigla do cilja ";
    private static final String YES = "DA";
    private static final String NO = "NE";

    private final int figureID;
    private final String figureType;
    private final FigureColor figureColor;
    private final boolean reachedToEnd;
    private final List<Integer> crossedFields;

    public FigureHistory(int figureID, String figureType, FigureColor figureColor, boolean reachedToEnd, List<Integer> crossedFields) {
        this.figureID = figureID;
        this.figureType = figureType;
        this.figureColor = figureColor;
        this.reachedToEnd = reachedToEnd;
        this.crossedFields = crossedFields;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("   ");
        stringBuilder.append(FIGURE).append(figureID).append(' ');
        stringBuilder.append('(').append(figureType).append(", ").append(figureColor).append(')');
        stringBuilder.append(" - ").append(CROSSED_PATH);
        stringBuilder.append('(');
        for (int crossedField : crossedFields) {
            stringBuilder.append(crossedField).append('-');
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("-"));
        stringBuilder.append(")").append(" - ").append(REACHED_TO_END);
        stringBuilder.append(reachedToEnd ? YES : NO).append('\n');

        return stringBuilder.toString();
    }
}
