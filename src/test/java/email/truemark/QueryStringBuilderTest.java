/**
 *    Copyright 2013-2016 PayStax, LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package email.truemark;

import email.truemark.http.QueryStringBuilder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Erik R. Jensen
 */
public class QueryStringBuilderTest {

	@Data
	@Accessors(chain = true)
	public class TestObject {
		private String test1;
		private String test2;
	}

	@Test
	public void testToQueryString() {
		QueryStringBuilder qb = new QueryStringBuilder();
		qb.add(new TestObject().setTest1("test1").setTest2("test2"));
		String queryString = qb.toQueryString();
		assertThat(queryString, equalTo("test1=test1&test2=test2"));
	}

	@Test
	public void testEncoddedQueryString1() {
		QueryStringBuilder qb = new QueryStringBuilder();
		qb.add("test1", "m&m");
		String queryString = qb.toQueryString();
		assertThat(queryString, equalTo("test1=m%26m"));
	}

	@Test
	public void testEncodedQuwryString2() {
		QueryStringBuilder qb = new QueryStringBuilder();
		qb.add(new TestObject().setTest1("m&m").setTest2("x=y"));
		String queryString = qb.toQueryString();
		assertThat(queryString, equalTo("test1=m%26m&test2=x%3Dy"));
	}
}
