/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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
 *
 */

package walkingkooka.tree.json;

import org.junit.jupiter.api.Test;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.ListTesting2;
import walkingkooka.collect.map.Maps;

public final class JsonObjectNodeListTest implements ListTesting2<JsonObjectNodeList, JsonNode>,
        ToStringTesting<JsonObjectNodeList> {

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createList(), "[first=true, second=false]");
    }

    @Override
    public JsonObjectNodeList createList() {
        return JsonObjectNodeList.with(Maps.of(JsonNodeName.with("first"), JsonNode.booleanNode(true),
                JsonNodeName.with("second"), JsonNode.booleanNode(false)));
    }

    @Override
    public Class<JsonObjectNodeList> type() {
        return JsonObjectNodeList.class;
    }
}
