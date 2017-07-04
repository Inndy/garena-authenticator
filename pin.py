import base64
import hashlib
import hmac
import struct
import sys
import time

INTERVAL = 180

def get_current_time_interval(interval=INTERVAL):
    return int(time.time() // interval)

def otp(key):
    if len(key) != 16:
        return None

    try:
        key = base64.b32decode(key)
    except:
        return None

    t = get_current_time_interval()
    h = hmac.HMAC(key, struct.pack('>Q', t), hashlib.sha1).digest()
    i = bytearray(h)[-1] & 15
    r = h[i:i+4]
    v = struct.unpack('>I', r)[0] & 0x7fffffff
    return '%.6d' % (v % 1000000)

def progressbar(percentage, width=60):
    W = width - 2
    w = int(W * percentage / 100)

    return '[%s%s]' % ('#' * w, ' ' * (W - w))

def main(argv):
    if len(argv) < 2:
        print('Usage: {script} key'.format(script=argv[0]))
        return

    last_tick = get_current_time_interval()
    code = otp(argv[1])

    while True:
        t = get_current_time_interval()

        if last_tick != t:
            last_tick = t
            code = otp(argv[1])

        if not code:
            print('Invalid key')
            break

        left = (get_current_time_interval() + 1) * INTERVAL - time.time()
        sys.stdout.write('%s -> %s (%5.2f secs left)\r' % (progressbar((1 - left / INTERVAL) * 100), code, left))
        sys.stdout.flush()
        time.sleep(0.3)


if __name__ == '__main__':
    main(sys.argv)
