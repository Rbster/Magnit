package ru.rbster;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Entry {
    @XmlElement(name = "field")
    public Long field;
    Entry(){};
}
