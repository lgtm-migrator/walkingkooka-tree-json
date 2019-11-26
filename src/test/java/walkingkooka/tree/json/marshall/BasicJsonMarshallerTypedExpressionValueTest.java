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

package walkingkooka.tree.json.marshall;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.tree.expression.BigIntegerExpression;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeException;

import java.math.BigInteger;

public final class BasicJsonMarshallerTypedExpressionValueTest extends BasicJsonMarshallerTypedExpressionTestCase<BasicJsonMarshallerTypedExpressionValue<BigIntegerExpression, java.math.BigInteger>, BigIntegerExpression> {

    @Test
    public final void testFromBooleanFails() {
        this.unmarshallFailed(JsonNode.booleanNode(true), JsonNodeException.class);
    }

    @Test
    public final void testFromNumberFails() {
        this.unmarshallFailed(JsonNode.number(123), JsonNodeException.class);
    }

    @Test
    public final void testFromObjectFails() {
        this.unmarshallFailed(JsonNode.object(), JsonNodeException.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    BasicJsonMarshallerTypedExpressionValue marshaller() {
        return BasicJsonMarshallerTypedExpressionValue.with(Expression::bigInteger,
                BigIntegerExpression.class,
                BigInteger.class);
    }

    @Override
    BigIntegerExpression value() {
        return Expression.bigInteger(BigInteger.valueOf(1234));
    }

    @Override
    JsonNode node() {
        return JsonNode.string(this.value().toString());
    }

    @Override
    String typeName() {
        return "big-integer-expression";
    }

    @Override
    Class<BigIntegerExpression> marshallerType() {
        return BigIntegerExpression.class;
    }

    @Override
    public Class<BasicJsonMarshallerTypedExpressionValue<BigIntegerExpression, java.math.BigInteger>> type() {
        return Cast.to(BasicJsonMarshallerTypedExpressionValue.class);
    }
}
