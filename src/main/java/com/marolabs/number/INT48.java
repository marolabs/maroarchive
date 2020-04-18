/**
 * 
 * Copyright 2020 Marolabs(TM) Co,Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.marolabs.number;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.marolabs.util.Bitz;
import com.marolabs.util.pool.ObjectPool;

public final class INT48 extends SingnedInteger<INT48> {
	public static final int SIZE = 48;
	public static final int BYTES = SIZE / Byte.SIZE;

	private long val;

	private boolean disposed = false;

	private static final ObjectPool<INT48> s_pool = Reuse.getPool(INT48.class);

	public static INT48 create(long val) {
		return s_pool.alloc().set(val);
	}

	public static INT48 create(Number val) {
		return s_pool.alloc().set(val.longValue() & 0xFFFFFFFFFFFFL);
	}

	public INT48 duplicate() {
		return INT48.create(this);
	}

	public INT48 set(int val) {
		this.val = val;
		return this;
	}

	@Override
	public INT48 set(Number newVal) {
		return this.set(newVal.longValue() & 0xFFFFFFFFFFFFL);
	}

	@Override
	public void _init() {
		val = 0;
		disposed = false;
	}

	@Override
	public void _cleanup() {
	}

	@Override
	public boolean disposed() {
		return disposed;
	}

	@Override
	public void dispose() {
		if (!disposed) {
			disposed = true;
			s_pool.free(this);
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		Bitz.putInt(out, val, BYTES, true);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException {
		val = Bitz.getInt64(in, BYTES, true);
	}

	@Override
	public int intValue() {
		return (int) val;
	}

	@Override
	public long longValue() {
		return val & 0xFFFFFFFFFFFFL;
	}

	@Override
	public float floatValue() {
		return val;
	}

	@Override
	public double doubleValue() {
		return val;
	}

	@Override
	public int compareTo(INT48 o) {
		return val == o.val ? 0 : val > o.val ? 1 : -1;
	}

	@Override
	public int bytes(byte[] out, int off, int len) {
		if (off + BYTES > out.length || len < BYTES) {
			throw new IndexOutOfBoundsException(off + " +" + BYTES + "  > " + out.length + " or " + len + "< " + BYTES);
		}

		Bitz.putInt(out, off, val, BYTES);
		return BYTES;
	}

	@Override
	public INT48 add(INT48 val) {
		this.val += val.val;

		return this;
	}

	@Override
	public INT48 subtract(INT48 val) {
		this.val -= val.val;

		return this;
	}

	@Override
	public INT48 mul(INT48 val) {
		this.val *= val.val;

		return this;
	}

	@Override
	public INT48 divide(INT48 val) {
		this.val /= val.val;

		return this;
	}

	@Override
	public INT48 mod(INT48 val) {
		this.val %= val.val;

		return this;
	}

	@Override
	public INT48 shiftLeft(int bits) {
		this.val = val << bits;

		return this;
	}

	@Override
	public INT48 shiftRight(int bits) {
		this.val = val >> bits;

		return this;
	}

	@Override
	public INT48 shiftSignRight(int bits) {
		this.val = val >>> bits;

		return this;
	}

	@Override
	public INT48 and(INT48 val) {
		this.val &= val.val;

		return this;
	}

	@Override
	public INT48 or(INT48 val) {
		this.val |= val.val;

		return this;
	}

	@Override
	public INT48 xor(INT48 val) {
		this.val ^= val.val;

		return this;
	}

	@Override
	public <S extends MutableNumber<S>> S cast(Class<S> type) {
		// TODO Auto-generated method stub
		return null;
	}
}