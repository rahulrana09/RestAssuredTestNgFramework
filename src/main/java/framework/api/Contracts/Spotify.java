package framework.api.Contracts;

import framework.api.utils.JsonPathOperation;

import static framework.api.utils.MultiLineString.multiLineString;
import static framework.api.utils.Utils.json;

// todo check where Lombok could be a better use
public class Spotify {
    public static String getCreatePlayListJson(JsonPathOperation... operations) {
        return json(multiLineString(/*
            {
                "name": "New Playlist",
                "description": "New playlist description",
                "public": false
            }
        */), operations);
    }

    public static String getCreateWorkSpaceJson(JsonPathOperation... operations) {
        return json(multiLineString(/*
            {
                "workspace":{
                        "name": "ScriptWS-01",
                        "type": "personal",
                        "description": "Created New Workspace"
                    }
            }
        */), operations);
    }

    public static String getCreateCollectionJson(JsonPathOperation... operations) {
        return json(multiLineString(/*
            {
            "collection": {
                "info": {
                    "_postman_id": "c0b64c19-29d3-4fc3-801f-f7861a7bc836",
                    "name": "CollectionHierarchy",
                    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
                    "updatedAt": "2024-04-29T11:28:31.000Z",
                    "uid": "2760657-c0b64c19-29d3-4fc3-801f-f7861a7bc836",
                    "createdAt": "2024-04-29T11:27:31.000Z",
                    "lastUpdatedBy": "2760657"
                },
                "item": [
                    {
                        "name": "New Folder",
                        "item": [
                            {
                                "name": "req-1",
                                "id": "63fd23de-f74d-4cde-a903-250a38e066b6",
                                "protocolProfileBehavior": {
                                    "disableBodyPruning": true
                                },
                                "request": {
                                    "method": "GET",
                                    "header": []
                                },
                                "response": [],
                                "uid": "2760657-63fd23de-f74d-4cde-a903-250a38e066b6"
                            }
                        ],
                        "id": "80dc63d3-e570-4416-a3f2-177e3e4fed53",
                        "uid": "2760657-80dc63d3-e570-4416-a3f2-177e3e4fed53"
                    }
                ]
            }
        }
        */), operations);
    }

}
