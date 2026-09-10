# Create: Enchantable Machinery

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81cbbec6d942e49b34df
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Enchantable Machinery
- **Arquivo JAR:** `createenchantablemachinery-3.6.0+mc1.21.1-neoforge.jar`
- **Versão 1.21.1:** 3.6.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Magia, Automação
- **Função:** Permite aplicar encantamentos vanilla a máquinas/blocos Create selecionados, alterando desempenho ou comportamento conforme o encantamento.
- **Dependências:** Create obrigatório. Pack físico também contém Jade 15.10.6, Apotheosis 8.8.0, Apothic Enchanting 1.6.2 e Create: Enchantment Industry 2.5.3b, relevantes para compatibilidade/balanceamento.
- **Sobreposição:** Diferente de Create: Enchantment Industry: Enchantable Machinery aplica enchantments às próprias máquinas; Enchantment Industry automatiza XP/enchanted-item processing. Interação com Apothic exige balanceamento, não remoção automática.
- **Compatibilidade/Riscos:** Riscos: enchant data loss em break/place; drop/output duplication; Mixer overstress restart; Silk Touch Plough collect-or-drop; modded enchant applicability; Apothic scaling; Jade/goggles stale display; glint/shader issues.
- **Observações:** JAR/mod id/runtime 3.6.0 confirmados. Release 3.6.0 corrige Spout sound, Mixer recovery after overstress, Jade names e Silk Touch Plough snow/collect-or-drop. Source público consultado expõe documentação, não codebase matching completa.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release oficial 3.6.0 + README oficial cotrin8672/CreateEnchantableMachinery.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-enchantable-machinery
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 3.6.0 com machine enchant state, Enchanting Table/goggles, modded enchants, Mixer overstress recovery, Spout/Jade/Silk Touch Plough fixes e lifecycle catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> ✨ **Identidade física confirmada:** `createenchantablemachinery-3.6.0+mc1.21.1-neoforge.jar`, mod id `createenchantablemachinery`, runtime `3.6.0`. A release 3.6.0 é NeoForge 1.21.1, Client & Server.

## 1. Papel e authority
Create: Enchantable Machinery permite aplicar enchantments a determinados blocos/máquinas Create para alterar função/eficiência e presentation. O addon owns a associação enchantment↔machine e persistência desse estado; Create continua owner da máquina/processo base e Minecraft/Apothic providers continuam owners dos enchantments.

## 2. Máquinas explicitamente publicadas
A documentação do projeto confirma suporte planejado/implementado para **Mechanical Drill, Mechanical Saw e Mechanical Harvester**. Outras máquinas presentes na build não são enumeradas aqui sem evidência pública exata; o registry/runtime é authority para a lista completa.

## 3. Enchanting Table
Blocos suportados podem ser encantados por Enchanting Table. A operação deve produzir um machine stack/state com enchantment data válido e custo aplicado uma única vez, sem criar cópia encantada mantendo o bloco original indevidamente.

## 4. Enchantments de outros mods
O projeto anuncia compatibilidade com enchantments externos e usa Smelting de Majrusz como exemplo histórico. Compatibilidade genérica não significa que todo enchantment de qualquer mod faça sentido em toda máquina; applicability real precisa ser validada por tags/hooks da build.

## 5. Goggles e leitura de estado
Ao usar goggles, o jogador pode ver quais enchantments uma máquina possui. Essa visualização é derivada do state persistido; overlay não é authority e não deve mostrar enchantment que o servidor já removeu.

## 6. Persistência do enchantment
Place, break, pick-up, schematic/contraption movement quando suportado e restart precisam preservar ou remover enchantment conforme a semântica do addon. O principal risco é perder data no caminho block entity/block state/item stack.

## 7. Mechanical Mixer — fix 3.6.0
A 3.6.0 corrige o **Mechanical Mixer encantado não reiniciando após recuperação de overstress**. Regression gate: overstress→speed recovery precisa rearmar a máquina sem duplicar recipe progress nem permanecer travada.

## 8. Spout — fix 3.6.0
A mesma release corrige playback de som de processamento em Spout encantado. Som é client-facing, mas deve corresponder a um único processamento real; não usar áudio como prova de consumo/output server-side.

## 9. Silk Touch Plough — fix 3.6.0
A 3.6.0 corrige snow drops do Plough com Silk Touch e lógica de collect-or-drop. Isso toca diretamente conservation: output deve ser inserido/coletado ou derrubado uma única vez, nunca ambos.

## 10. Jade — fix 3.6.0
A release corrige nomes de blocos encantados no Jade. O pack contém Jade 15.10.6, então o delta é concretamente testável. Jade é presentation; nome incorreto não pode mudar registry identity do bloco.

## 11. Apothic Enchanting/Apotheosis
O pack contém **Apotheosis 8.8.0** e **Apothic Enchanting 1.6.2**. O risco real é applicability/power de enchantments externos em máquinas, não duplicidade com Enchantment Industry. Cada enchantment aceito precisa manter semântica coerente e não multiplicar drops/processos de forma explosiva.

## 12. Create: Enchantment Industry
Create: Enchantment Industry 2.5.3b está instalado. Ele automatiza XP/enchanted-item processing; Enchantable Machinery aplica enchantments às próprias máquinas. As funções são complementares, embora possam formar loops de aquisição/aplicação que exigem balanceamento.

## 13. Glint experimental
O glint de enchantment em blocos no mundo é descrito como experimental e pode interagir mal com shaders/Flywheel. Render failure deve degradar visualmente sem corromper machine state. O pack gráfico precisa ser testado separadamente.

## 14. Client/server
Enchant application, stored enchantments, machine effects, drops e processing são server-authoritative. Glint, goggles info, sound e Jade display são client-facing. Cliente não pode adicionar efeito funcional só por alterar tooltip/render.

## 15. Overstress e recipe progress
Enchantment não deve violar a state machine do Create. Quando uma máquina fica overstressed, processing precisa pausar; ao recuperar, retoma conforme provider sem processar a mesma recipe duas vezes. O fix do Mixer torna esse gate obrigatório em 3.6.0.

## 16. Break/placement e item data
Quebrar máquina encantada é um boundary crítico. Se enchantments acompanham o item, data components/NBT devem ser preservados; se a semântica define perda, isso deve ocorrer explicitamente. Nenhuma política é inventada além do observado em runtime.

## 17. Lifecycle
Testar enchant table, place/break, goggles/Jade, overstress recovery, contraption assemble/disassemble quando aplicável, chunk reload, restart, shader/resource reload e modded enchantments. `/reload` de tags/enchantments também merece smoke-test.

## 18. Riscos
1. Enchantment data perdido em break/place.
2. Enchantment duplica drops ou recipe outputs.
3. Mixer não reinicia ou reinicia duas vezes após overstress.
4. Silk Touch Plough coleta e dropa simultaneamente.
5. Modded enchantment é aceito em machine incompatível.
6. Apothic power scaling quebra balanceamento.
7. Jade/goggles mostram state stale.
8. Glint experimental conflita com shader/Flywheel.
9. Spout toca som múltiplo para um único process.
10. Schematic/contraption stripam enchant data.

## 19. Matriz de testes
- [ ] Dedicated server inicia com 3.6.0 + Create 6.0.10.
- [ ] Drill/Saw/Harvester suportados recebem e preservam enchantments válidos.
- [ ] Enchanting Table consome custo uma vez.
- [ ] Mixer encantado recupera de overstress conforme fix 3.6.0.
- [ ] Spout encantado processa uma vez e toca áudio coerente.
- [ ] Silk Touch Plough snow drop não duplica.
- [ ] Jade 15.10.6 mostra nome correto.
- [ ] Goggles mostram enchantments atuais sem cache stale.
- [ ] Apothic Enchanting 1.6.2 não injeta efeitos inválidos/duplicados.
- [ ] Break/place/restart preservam o state conforme provider.
- [ ] Shader/resource reload não afeta state funcional.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 20. Evidências e limites
A modlist física confirma 3.6.0. A documentação oficial confirma enchant de blocos Create, goggles, enchantments externos e Enchanting Table; a release 3.6.0 confirma fixes de Spout, Mixer, Jade e Silk Touch Plough. O repositório público consultado expõe documentação, mas não source code matching completo; classes e lista total de máquinas ficam fail-closed.

> 🔒 **Boundary canônico:** Enchantable Machinery owns o estado de enchantment aplicado à máquina; Create owns a state machine mecânica. Enchantment nunca deve bypassar conservation, overstress ou authority do servidor.
