# Garena-Authenticator

This program implements same OTP algorithm with [Garena](https://www.garena.tw/).

## Algorithm

Input `key` is 16chars base-32 encoded string.

```python
# 1. decode the key
raw_key = base32.decode(key)
# 2. compute time interval (`time` is integer)
time = epoch_seconds() / 180
# 3. encode time interval as 8bytes big-endian
time_bytes = int64_to_bigendian(time)
# 4. compute HMAC-SHA1 hash value, raw_out is bytes
raw_out = hmac_sha1(time_bytes, sign_key=raw_key)
# 5. take last byte
last_byte = raw_out[19]
# 6. compute index
index = last_byte % 16
# 7. take 4bytes sub-sequance from raw_out,
sequance = subbytes(raw_out, index, 4) # take 4bytes from raw_out
# 8. decode as big-endian integer and remove highest bit
number = bigendian_to_int32(sequance) & 0x7fffffff
# 9. compute OTP code
otp_code = padding_zero(number % 1000000, 6)
```

## TODO

- [x] Extract code from Android App
- [x] Python version
