/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.sw.definition;

import jsinterop.annotations.JsType;
import org.kie.workbench.common.stunner.client.json.mapper.annotation.JSONMapper;
import org.kie.workbench.common.stunner.client.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.workbench.common.stunner.client.yaml.mapper.api.annotation.YamlPropertyOrder;

@JsType
@JSONMapper
@YAMLMapper
@YamlPropertyOrder({"name", "type", "end", "onErrors", "eventTimeout", "compensatedBy", "stateDataFilter", "transition", "timeouts"})
public class State<T extends State<T>> {

    public String name;

    public String type;

    public State() {
        this.name = "State";
    }

    public T setName(String name) {
        this.name = name;
        return (T) this;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public T setType(String type) {
        this.type = type;
        return (T) this;
    }
}
