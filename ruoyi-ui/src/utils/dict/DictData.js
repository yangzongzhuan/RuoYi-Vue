/**
 * @classdesc 字典数据
 * @property {String} label 标签
 * @property {*} value 标签
 * @property {Object} raw 原始数据
 */
export default class DictData {
  constructor(label, value, raw) {
    this.label = label
    this.value = value
    this.raw = raw
  }

  /**
   * 根据字典类型的valueType转换字典值的类型
   * @param {String} valueType 数据类型
   */
  convertValueType(valueType) {
    if (!valueType) {
      return this.value
    }

    const value = this.value
    switch (valueType.toLowerCase()) {
      case 'int':
        return parseInt(value)
      case 'long':
        return parseInt(value)
      case 'double':
        return parseFloat(value)
      case 'boolean':
        return value === 'true' || value === '1'
      default:
        return value
    }
  }
}
