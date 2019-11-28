package com.queue.demo.util;

import com.queue.demo.domain.Metadata;
import com.queue.demo.domain.Record;
import com.queue.demo.domain.Relation;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordXmlHandler extends DefaultHandler {

    private List<Record> records = new ArrayList<Record>();
    private List<Metadata> metadatas = new ArrayList<Metadata>();
    private List<Metadata> relationMetadatas = new ArrayList<Metadata>();
    private List<Relation> relations = new ArrayList<Relation>();

    private Metadata metadata;
    private Metadata relationMetadata;
    private Record record;
    private Record relationRecord;
    private Relation relation;
    boolean isMetadata = false;
    boolean isRelationMetadata = false;
    boolean isRelations = false;
    boolean isRelation = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // 清空历史数据
        sb.setLength(0);
        if (qName.equals("record") && !isRelation) {
            record = new Record();
            String metedataStandard = attributes.getValue("metedataStandard");
            record.setMetedataStandard(metedataStandard);
        }
        if (qName.equals("metadata")) {
            isMetadata = true;
            metadata = new Metadata();
        }
        if (qName.equals("ListRelations")) {
            isRelations = true;
        }
        if (qName.equals("relation")) {
            isRelation = true;
            relation = new Relation();
        }
        if (isRelation && qName.equals("record")) {
            isRelation = true;
            String metedataStandard = attributes.getValue("metedataStandard");

            relationRecord = new Record();
            relationRecord.setMetedataStandard(metedataStandard);
        }
        if (qName.equals("metadata") && isRelation) {
            isRelationMetadata = true;
            relationMetadata = new Metadata();
        }
    }

    BufferedReader br = null;
    FileReader reader = null;

    @Override
    public void startDocument() throws SAXException {

        super.startDocument();
    }

    // 标签中的内容，临时变量，重新开始时需清空
    private StringBuilder sb = new StringBuilder();

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String content = sb.toString().trim();
        if (qName.equals("identifier")) {
            if (isRelation) {
                relationRecord.setIdentifier(content);
            } else {
                record.setIdentifier(content);
            }
        }
        if (qName.equals("state")) {
            if (isRelation) {
                relationRecord.setState(content);
            } else {
                record.setState(content);
            }
        }
        if (qName.equals("errorMessage")) {
            if (isRelation) {
                relationRecord.setErrorMessage(content);
            } else {
                record.setErrorMessage(content);
            }
        }
        if (qName.equals("url")) {
            if (isRelation) {
                relationRecord.setUrl(content);
            } else {
                record.setUrl(content);
            }
        }
        if (!isRelation && qName.equals("metadata")) {
            record.setMetadatas(metadatas);
            metadatas = new ArrayList<Metadata>();
            isMetadata = false;
        }
        if (isMetadata) {
            if (qName.equals("title")) {
                metadata.setTitle(content);
            }
            if (qName.equals("value")) {
                metadata.setValue(content);
            }
            if (!qName.equals("title") && !qName.equals("value")) {
                metadata.setCode(qName);
                metadatas.add(metadata);
                metadata = new Metadata();
            }
        }

        if (qName.equals("relationId")) {
            relation.setRelationId(content);

        }
        if (qName.equals("parentName")) {
            relation.setParentName(content);

        }
        if (qName.equals("relationOptionId")) {
            relation.setRelationOptionId(content);

        }
        if (qName.equals("relationOptionName")) {
            relation.setRelationOptionName(content);

        }
        if (qName.equals("relationMasterName")) {
            relation.setRelationMasterName(content);

        }
        if (qName.equals("relationSlaveName")) {
            relation.setRelationSlaveName(content);

        }
        if (qName.equals("relationName")) {
            relation.setRelationName(content);

        }
        if (qName.equals("relationOrder")) {
            relation.setRelationOrder(content);

        }
        if (isRelation && qName.equals("metadata")) {
            relationRecord.setMetadatas(relationMetadatas);
            relationMetadatas = new ArrayList<Metadata>();
            isRelationMetadata = false;
        }
        if (isRelationMetadata) {
            if (qName.equals("title")) {
                relationMetadata.setTitle(content);
            }
            if (qName.equals("value")) {
                relationMetadata.setValue(content);
            }
            if (!qName.equals("title") && !qName.equals("value")) {
                relationMetadata.setCode(qName);
                relationMetadatas.add(relationMetadata);
                relationMetadata = new Metadata();
            }
        }

        if (qName.equals("relation")) {
            isRelation = false;
            relation.setRelationRecord(relationRecord);
            relations.add(relation);
        }
        if (qName.equals("ListRelations")) {
            isRelations = false;
            record.setRelations(relations);
        }
        if (qName.equals("record") && !isRelation) {
            records.add(record);
        }
    }

    public List<Record> getRecords() {
        return records;
    }


    public void setRecords(List<Record> records) {
        this.records = records;
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        sb.append(ch, start, length);
    }

    @Override
    public void endDocument() throws SAXException {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e1) {
            }
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e1) {
            }
        }
        super.fatalError(e);
    }

}
